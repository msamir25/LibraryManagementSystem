package com.example.libraryManagementApp.config;


import com.example.libraryManagementApp.security.CustomUserDetailService;
import com.example.libraryManagementApp.security.JwtAuthenticationEntryPoint;
import com.example.libraryManagementApp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


        @Autowired
        private CustomUserDetailService userDetailService;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter(){
                return  new JwtAuthenticationFilter();
        }
        @Bean
        PasswordEncoder passwordEncoder(){
                return new BCryptPasswordEncoder();
        }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        //.csrf(Customizer.withDefaults())
                        .csrf(AbstractHttpConfigurer::disable)
                        .exceptionHandling( httpSecurityExceptionHandlingConfigurer ->
                                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                        .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers(HttpMethod.POST ,"/api/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.GET , "/api/books").hasAnyRole("LIBRARIAN" , "PATRON")
                                         .requestMatchers(HttpMethod.GET , "/api/books/**").hasAnyRole("LIBRARIAN" , "PATRON")
                                         .requestMatchers(HttpMethod.POST , "/api/books").hasRole("LIBRARIAN")
                                         .requestMatchers(HttpMethod.PUT , "/api/books/**").hasRole("LIBRARIAN")
                                         .requestMatchers(HttpMethod.DELETE , "/api/books/**").hasRole("LIBRARIAN")

                                         .requestMatchers(HttpMethod.GET, "/api/patrons").hasAnyRole("PATRON" , "LIBRARIAN")
                                         .requestMatchers(HttpMethod.GET, "/api/patrons/**").hasAnyRole("PATRON" , "LIBRARIAN")
                                         .requestMatchers(HttpMethod.POST, "/api/patrons").hasRole("PATRON")
                                         .requestMatchers(HttpMethod.PUT, "/api/patrons/**").hasRole("PATRON")
                                         .requestMatchers(HttpMethod.DELETE, "/api/patrons/**").hasRole("PATRON")

                                         .requestMatchers(HttpMethod.POST, "/api/borrow/**").hasRole("PATRON")
                                         .requestMatchers(HttpMethod.PUT, "/api/return/**").hasRole("PATRON")
                                .anyRequest().authenticated()
                        )
                        .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http.addFilterBefore(jwtAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);
                return http.build();

        }


       @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }


}
