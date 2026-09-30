package EducacionIt.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,   // Habilita @PreAuthorize, @PostAuthorize
        securedEnabled = true,   // Habilita @Secured
        jsr250Enabled = true     // Habilita @RolesAllowed, @PermitAll, @DenyAll
)
@Configuration
public class SecurityConfig {

    /*@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;*/

    @Value("${app.cors.allowed-origins:http://localhost:8080,http://localhost:80}")
    private String allowedOrigins;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir orígenes desde variable de entorno (puede ser múltiples separados por coma)
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Permitir credenciales (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // Exponer headers en la respuesta
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // Tiempo de cache de configuración CORS (en segundos)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
