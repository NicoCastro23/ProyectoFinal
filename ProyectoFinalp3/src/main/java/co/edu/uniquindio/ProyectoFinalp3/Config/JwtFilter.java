package co.edu.uniquindio.ProyectoFinalp3.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import co.edu.uniquindio.ProyectoFinalp3.security.CustomAuthentication;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Aquí puedes extraer y validar el token JWT desde el encabezado de la
        // solicitud
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Valida el token JWT aquí (puedes usar una clase de servicio para esto)
            // Si es válido, autentica al usuario en el contexto de seguridad
            if (isValidToken(token)) {
                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
            }
        }

        // Continúa la cadena de filtros
        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(String token) {
        // Implementa la lógica de validación del token
        return true; // Esto es solo un ejemplo; asegúrate de validar correctamente el token
    }

    private CustomAuthentication getAuthentication(String token) {
        // Aquí creamos una instancia de CustomAuthentication con el token
        return new CustomAuthentication(token);
    }
}