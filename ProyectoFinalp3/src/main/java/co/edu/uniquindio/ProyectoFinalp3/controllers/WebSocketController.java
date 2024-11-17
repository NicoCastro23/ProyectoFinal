package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.Message;
import co.edu.uniquindio.ProyectoFinalp3.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final WebSocketService webSocketService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(WebSocketService webSocketService, SimpMessagingTemplate messagingTemplate) {
        this.webSocketService = webSocketService;
        this.messagingTemplate = messagingTemplate;
    }

    // Maneja los mensajes entrantes desde "/chat/message"
    @MessageMapping("/chat/message")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        try {
            // Guarda el mensaje en la base de datos
            webSocketService.saveAndSendMessage(message, message.getChat().getId().toString());

            // Envía el mensaje a los participantes del chat
            messagingTemplate.convertAndSend("/topic/messages/" + message.getChat().getId(), message);

            return message; // Devuelve el mensaje al cliente que lo envió
        } catch (Exception e) {
            System.err.println("Error procesando el mensaje: " + e.getMessage());
            return null; // Devuelve un valor nulo si ocurre un error
        }
    }

    // Enviar un mensaje privado
    @MessageMapping("/chat/private")
    public void handlePrivateMessage(Message message) {
        try {
            // Verifica que el receptor no sea nulo
            if (message.getReceiver() == null) {
                throw new IllegalArgumentException("El receptor no puede ser nulo.");
            }

            // Guarda el mensaje en la base de datos y envía al destinatario
            webSocketService.sendPrivateMessage(
                message.getUser().getId().toString(),
                message.getReceiver().getId().toString(),
                message
            );
        } catch (Exception e) {
            System.err.println("Error enviando mensaje privado: " + e.getMessage());
        }
    }
}
