package EducacionIt.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserService userService;

    /**
     * Método principal que procesa cada petición HTTP.
     * <p>
     * Flujo:
     * 1. Extrae el token JWT del header Authorization
     * 2. Valida el token
     * 3. Autentica al usuario en el SecurityContext de Spring Security
     *
     * @param request     Petición HTTP entrante
     * @param response    Respuesta HTTP
     * @param filterChain Cadena de filtros de Spring Security
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
// Obtener el header Authorization de la petición
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Verificar si existe el header y si tiene el formato correcto "Bearer {token}"
        // Si no cumple, continuar con la cadena de filtros sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token JWT (eliminar el prefijo "Bearer " - 7 caracteres)
        jwt = authHeader.substring(7);
        logger.info(jwt);
        logger.info(request.getRequestURI());
        logger.info(request.getServletPath());
        // Extraer el username desde el token JWT
        username = jwtUtil.extractUsername(jwt);

        // Verificar si:
        // 1. Se extrajo correctamente el username del token
        // 2. El usuario NO está ya autenticado en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Cargar los detalles del usuario desde la base de datos
            UserDetails userDetails = this.userService.loadUserByUsername(username);

            // Validar que el token sea válido (firma correcta, no expirado, pertenece al usuario)
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Crear el objeto de autenticación de Spring Security
                // Parámetros: principal (usuario), credentials (null porque usamos JWT), authorities (roles/permisos)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Agregar detalles adicionales de la petición HTTP (IP, sesión, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Establecer la autenticación en el SecurityContext
                // A partir de aquí, Spring Security reconoce al usuario como autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros (SIEMPRE debe ejecutarse)
        filterChain.doFilter(request, response);
    }
}