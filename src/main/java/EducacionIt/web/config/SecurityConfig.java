package EducacionIt.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                        // ####################### ACA CONFIGURAMOS EL ACCESO A NUESTROS ENDPOINTS/RECURSOS ##################################
                                .requestMatchers(HttpMethod.GET,  "/css/**","/js/**","/img/**", "/swagger-ui.html").permitAll() // Endpoints públicos
                                .requestMatchers("/public/**").permitAll() // Endpoints públicos
                                .requestMatchers("/persona/**").hasRole("ADMIN")
                                .requestMatchers("/").hasRole("PROHIBIDO")
                                .anyRequest().authenticated() // Todos los demás
                        //##################################################################################################################################
                )
                .httpBasic(Customizer.withDefaults()); // Habilita la autenticación básica
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Rosten")
                .password("1234")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("Nestor")
                .password("1234")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
