package co.edu.uniquindio.ProyectoFinalp3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import co.edu.uniquindio.ProyectoFinalp3.websocket.ChatWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/ws/chat")
                .setAllowedOrigins("*") // Ajusta según tus necesidades de CORS
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }

    public WebSocketHandler chatHandler() {
        return new ChatWebSocketHandler(); // Asegúrate de tener un handler para manejar los mensajes WebSocket
    }
}
