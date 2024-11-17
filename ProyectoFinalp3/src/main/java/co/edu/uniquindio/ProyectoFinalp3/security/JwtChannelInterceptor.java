package co.edu.uniquindio.ProyectoFinalp3.security;

import co.edu.uniquindio.ProyectoFinalp3.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.UUID;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    @Autowired
    public JwtChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            System.out.println("Authorization Header: " + authHeader);

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                try {
                    System.out.println("Token recibido: " + token);

                    // Extrae el userId del token usando JwtService
                    UUID userId = jwtService.extractUserId(token);
                    System.out.println("Token válido, usuario ID: " + userId);

                    // Asigna el usuario al StompPrincipal
                    accessor.setUser(new StompPrincipal(userId.toString()));
                } catch (Exception e) {
                    System.err.println("Error al validar el token: " + e.getMessage());
                    e.printStackTrace(); // Imprime la traza del error para depuración
                    throw new IllegalArgumentException("Token inválido o expirado.");
                }
            } else {
                System.err.println("Encabezado Authorization no proporcionado o malformado.");
                throw new IllegalArgumentException("Falta el encabezado 'Authorization' o está malformado.");
            }
        }

        return message; // Si todo está bien, pasa el mensaje al siguiente interceptor
    }
}

