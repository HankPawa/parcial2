package co.edu.usco.pw.springSecurityDB.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import co.edu.usco.pw.springSecurityDB.services.UserDetailsServiceImpl;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso público a Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Configurar las rutas protegidas
                .requestMatchers("/rector/**").hasRole("RECTOR")
                .requestMatchers("/docente/**").hasRole("DOCENTE")
                .requestMatchers("/estudiante/**").hasRole("ESTUDIANTE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .loginProcessingUrl("/login") 
                .successHandler(customAuthenticationSuccessHandler()) // Usar el manejador personalizado
                .failureUrl("/login?error=true") 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        return http.build();
    }

    // Custom AuthenticationSuccessHandler
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_RECTOR"))) {
                    response.sendRedirect("/rector/asignaturas");
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_DOCENTE"))) {
                    response.sendRedirect("/docente/asignaturas");
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"))) {
                    response.sendRedirect("/estudiante/asignaturas");
                } else {
                    response.sendRedirect("/login");
                }
            }
        };
    }
}