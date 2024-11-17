package co.edu.uniquindio.ProyectoFinalp3.services;

import co.edu.uniquindio.ProyectoFinalp3.models.Message;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Mapa para mantener las sesiones activas de los usuarios
    private final Map<String, WebSocketSession> userSessions = new HashMap<>();

    @Autowired
    public WebSocketService(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // Método para agregar una sesión de WebSocket de un usuario
    public void addUserSession(String userId, WebSocketSession session) {
        userSessions.put(userId, session);
    }

    // Método para eliminar una sesión de WebSocket de un usuario
    public void removeUserSession(String userId) {
        userSessions.remove(userId);
    }

    // Enviar un mensaje a un usuario específico (WebSocketSession)
    public void sendMessageToUser(String userId, Message message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message.getContent()));
            } catch (IOException e) {
                System.err.println("Error enviando mensaje a usuario: " + e.getMessage());
            }
        } else {
            System.out.println("El usuario con ID " + userId + " no está conectado.");
        }
    }

    // Método para guardar un mensaje y luego enviarlo a los participantes del chat
    public void saveAndSendMessage(Message message, String chatId) {
        try {
            // Guardar el mensaje en la base de datos
            messageRepository.save(message);

            // Enviar el mensaje a todos los participantes del chat
            messagingTemplate.convertAndSend("/topic/messages/" + chatId, message);
        } catch (Exception e) {
            System.err.println("Error guardando y enviando mensaje: " + e.getMessage());
        }
    }

    // Método para enviar mensajes privados
    public void sendPrivateMessage(String senderId, String receiverId, Message message) {
        try {
            // Establece el receptor como objeto `User` relacionado
            User receiver = message.getReceiver(); // Asegúrate de que el receptor está configurado en el mensaje
            if (receiver == null) {
                throw new IllegalArgumentException("El receptor no puede ser nulo.");
            }

            // Guardar el mensaje
            messageRepository.save(message);

            // Validar si el destinatario está conectado
            if (userSessions.containsKey(receiverId)) {
                // Enviar el mensaje al destinatario directamente
                messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages", message);

                // Notificar al remitente que el mensaje fue entregado
                messagingTemplate.convertAndSendToUser(senderId, "/queue/acknowledgements", "Mensaje enviado correctamente.");
            } else {
                System.out.println("El destinatario con ID " + receiverId + " no está conectado.");
            }
        } catch (Exception e) {
            System.err.println("Error enviando mensaje privado: " + e.getMessage());
        }
    }

    // Método para manejar sesiones activas (opcional para debug o auditorías)
    public Map<String, WebSocketSession> getActiveSessions() {
        return Collections.unmodifiableMap(userSessions);
    }

    // Limpieza de sesiones inactivas (puede ejecutarse en un cron job)
    public void cleanInactiveSessions() {
        userSessions.values().removeIf(session -> !session.isOpen());
    }
}
