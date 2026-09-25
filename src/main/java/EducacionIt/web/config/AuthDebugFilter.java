package EducacionIt.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
public class AuthDebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("===== DEBUG AUTH =====");
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("Método: " + request.getMethod());

        if (auth == null) {
            System.out.println("Authentication: null (no autenticado aún)");
        } else {
            System.out.println("Authentication class: " + auth.getClass().getSimpleName());
            System.out.println("Principal: " + auth.getPrincipal());
            System.out.println("Authenticated: " + auth.isAuthenticated());

            String roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));

            System.out.println("Authorities: [" + roles + "]");
        }
        System.out.println("======================");

        filterChain.doFilter(request, response);
    }
}