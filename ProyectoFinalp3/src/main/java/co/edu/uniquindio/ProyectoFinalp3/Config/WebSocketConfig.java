package co.edu.uniquindio.ProyectoFinalp3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registro de un endpoint de WebSocket
        registry.addEndpoint("/chat")
                .setAllowedOrigins("*") // Permite conexiones desde cualquier origen
                .withSockJS(); // Este es el endpoint que los clientes usarán para conectarse
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Se crea un broker para "/topic" que es donde los mensajes se envían
        registry.setApplicationDestinationPrefixes("/app"); // El prefijo para los mensajes enviados desde el cliente
    }
}
