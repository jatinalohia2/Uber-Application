package com.pisoft.uberApp.UberApplication.config;

import com.pisoft.uberApp.UberApplication.enums.Roles;
import com.pisoft.uberApp.UberApplication.filter.JwtAuthFilter;
import com.pisoft.uberApp.UberApplication.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 1 step : we have to enable
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public static final String[] PUBLIC_ROUTES = {"/auth/signUp" , "/auth/login" , "/swagger-ui/index.html" , "/v3/api-docs/**" , "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(auth->
                auth.requestMatchers(PUBLIC_ROUTES).permitAll()
                        .requestMatchers("/driver/onBoardNewDriver").hasRole(Roles.RIDER.name())
                        .requestMatchers("/driver/**").hasRole(Roles.DRIVER.name())
                        .requestMatchers("/rider/**" , "/ride/**").hasRole(Roles.RIDER.name())
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth->oAuth.failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                )

                .formLogin(AbstractHttpConfigurer::disable) ;
        return httpSecurity.build();
    }


    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return  configuration.getAuthenticationManager();
    }
}
