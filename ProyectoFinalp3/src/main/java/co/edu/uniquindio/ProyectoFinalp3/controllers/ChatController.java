package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.Chat;
import co.edu.uniquindio.ProyectoFinalp3.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Endpoint para obtener todos los chats de un usuario específico
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getUserChats(@PathVariable UUID userId) {
        List<Chat> chats = chatService.getChatsByUser(userId);
        return ResponseEntity.ok(chats);
    }

    // Endpoint para encontrar o crear un chat entre dos usuarios
    @PostMapping("/create")
    public ResponseEntity<Chat> createOrGetChat(@RequestParam String username1, @RequestParam String username2) {
        Chat chat = chatService.findOrCreateChat(username1, username2);
        return ResponseEntity.ok(chat);
    }
}
