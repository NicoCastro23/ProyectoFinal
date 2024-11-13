package co.edu.uniquindio.ProyectoFinalp3.services;

import co.edu.uniquindio.ProyectoFinalp3.models.Chat;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.repository.ChatRepository;
import co.edu.uniquindio.ProyectoFinalp3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    // Método para guardar un mensaje de chat
    public Chat saveMessage(Chat chatMessage) {
        return chatRepository.save(chatMessage);
    }

    // Método para encontrar o crear un chat entre dos usuarios específicos
    public Chat findOrCreateChat(String username1, String username2) {
        Optional<User> user1 = userRepository.findByUsername(username1);
        Optional<User> user2 = userRepository.findByUsername(username2);

        if (user1.isPresent() && user2.isPresent()) {
            List<Chat> existingChats = chatRepository.findByUser1OrUser2(user1.get(), user2.get());
            if (!existingChats.isEmpty()) {
                // Devuelve el primer chat existente si ya existe entre estos usuarios
                return existingChats.get(0);
            } else {
                // Crea un nuevo chat si no existe entre los usuarios
                Chat newChat = new Chat();
                newChat.setUser1(user1.get());
                newChat.setUser2(user2.get());
                return chatRepository.save(newChat);
            }
        } else {
            throw new IllegalArgumentException("Uno o ambos usuarios no fueron encontrados.");
        }
    }

    // Método para obtener todos los chats en los que participa un usuario
    public List<Chat> getChatsByUser(UUID userId) {
        return chatRepository.findByUser1_IdOrUser2_Id(userId, userId);
    }
}

