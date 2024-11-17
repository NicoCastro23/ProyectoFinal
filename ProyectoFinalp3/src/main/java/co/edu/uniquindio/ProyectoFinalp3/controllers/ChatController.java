package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.Chat;
import co.edu.uniquindio.ProyectoFinalp3.models.ChatParticipant;
import co.edu.uniquindio.ProyectoFinalp3.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Crear un nuevo chat.
     *
     * @param body Datos del chat: name, userId1 y userId2
     * @return Chat creado
     */
    @PostMapping
    public ResponseEntity<?> createChat(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            UUID userId1 = UUID.fromString(body.get("userId1")); // Primer participante
            UUID userId2 = UUID.fromString(body.get("userId2")); // Segundo participante

            Chat createdChat = chatService.createChat(name, userId1, userId2);
            return ResponseEntity.ok(createdChat);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: Datos de entrada inválidos. Verifica los UUID y el formato del cuerpo de la solicitud.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el chat. " + e.getMessage());
        }
    }

    /**
     * Agregar un participante a un chat.
     *
     * @param chatId ID del chat
     * @param userId ID del usuario
     * @param isAdmin Indica si el usuario es administrador
     * @return Participante agregado
     */
    @PostMapping("/{chatId}/participants")
    public ResponseEntity<?> addParticipantToChat(
            @PathVariable UUID chatId,
            @RequestParam UUID userId,
            @RequestParam(required = false, defaultValue = "false") boolean isAdmin) {
        try {
            ChatParticipant participant = chatService.addParticipantToChat(chatId, userId, isAdmin);
            return ResponseEntity.ok(participant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al agregar participante. " + e.getMessage());
        }
    }

    /**
     * Obtener un chat por ID.
     *
     * @param chatId ID del chat
     * @return Chat correspondiente al ID
     */
    @GetMapping("/{chatId}")
    public ResponseEntity<?> getChatById(@PathVariable UUID chatId) {
        Optional<Chat> chat = chatService.getChatById(chatId);
        if (chat.isPresent()) {
            return ResponseEntity.ok(chat.get());
        } else {
            return ResponseEntity.status(404).body("Error: Chat no encontrado.");
        }
}
    /**
     * Obtener todos los chats.
     *
     * @return Lista de todos los chats
     */
    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {
        List<Chat> chats = chatService.getAllChats();
        return ResponseEntity.ok(chats);
    }

    /**
     * Obtener todos los participantes de un chat.
     *
     * @param chatId ID del chat
     * @return Lista de participantes
     */
    @GetMapping("/{chatId}/participants")
    public ResponseEntity<?> getParticipants(@PathVariable UUID chatId) {
        try {
            List<ChatParticipant> participants = chatService.getParticipants(chatId);
            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener participantes. " + e.getMessage());
        }
    }

    /**
     * Eliminar un chat.
     *
     * @param chatId ID del chat
     * @return Mensaje indicando el resultado
     */
    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable UUID chatId) {
        boolean deleted = chatService.deleteChat(chatId);
        if (deleted) {
            return ResponseEntity.ok("Chat eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Error: Chat no encontrado.");
        }
    }
}

