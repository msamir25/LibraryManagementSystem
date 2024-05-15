package com.example.libraryManagementApp.controller;
import com.example.libraryManagementApp.Repository.RoleRepository;
import com.example.libraryManagementApp.Repository.UserRepository;
import com.example.libraryManagementApp.dto.JWTAuthResponse;
import com.example.libraryManagementApp.dto.LoginDto;
import com.example.libraryManagementApp.dto.SignupDto;
import com.example.libraryManagementApp.entity.Role;
import com.example.libraryManagementApp.entity.User;
import com.example.libraryManagementApp.exceptions.ResourceNotFoundException;
import com.example.libraryManagementApp.security.CustomUserDetailService;
import com.example.libraryManagementApp.security.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse>authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        //get token from token providor

        final UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.getUsernameOrEmail());
        String token = jwtTokenProvider.generateToken(userDetails);
       return  new ResponseEntity<>(new JWTAuthResponse(token) , HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){
        //check if username is exist in DB
        if (userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("username is already taken" , HttpStatus.BAD_REQUEST);
        }

        //check if email in DB

        if (userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email is already taken" , HttpStatus.BAD_REQUEST);
        }

        //Add a new user
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role role = new Role();
        if (signupDto.getRole().equalsIgnoreCase("librarian")) {
            role.setName("ROLE_LIBRARIAN");
        } else if (signupDto.getRole().equalsIgnoreCase("patron")) {
            role.setName("ROLE_PATRON");
        }
        Role savedRole =  roleRepository.save(role);
        user.setRoles(Collections.singleton(savedRole));
        userRepository.save(user);


        return new ResponseEntity<>("user registed successfully" , HttpStatus.OK);

    }


    }









