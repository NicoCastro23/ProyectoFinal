package co.edu.uniquindio.ProyectoFinalp3.services;

import co.edu.uniquindio.ProyectoFinalp3.models.Chat;
import co.edu.uniquindio.ProyectoFinalp3.models.ChatParticipant;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.repository.ChatParticipantRepository;
import co.edu.uniquindio.ProyectoFinalp3.repository.ChatRepository;
import co.edu.uniquindio.ProyectoFinalp3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, 
                       ChatParticipantRepository chatParticipantRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.chatParticipantRepository = chatParticipantRepository;
        this.userRepository = userRepository;
    }

    public Chat createChat(String name, UUID userId1, UUID userId2) {
        // Buscar usuarios en la base de datos
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new IllegalArgumentException("Usuario 1 no encontrado"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new IllegalArgumentException("Usuario 2 no encontrado"));
    
        // Crear el chat
        Chat chat = new Chat(name, user1, user2);
    
        // Guardar el chat
        chat = chatRepository.save(chat);
    
        // Crear participantes
        ChatParticipant participant1 = new ChatParticipant(chat, user1, true);
        ChatParticipant participant2 = new ChatParticipant(chat, user2, false);
    
        // Guardar participantes
        chatParticipantRepository.save(participant1);
        chatParticipantRepository.save(participant2);
    
        return chat;
    }
    

    // Obtener un chat por ID
    public Optional<Chat> getChatById(UUID id) {
        return chatRepository.findById(id);
    }

    // Obtener todos los chats
    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    // Eliminar un chat
    public boolean deleteChat(UUID id) {
        if (chatRepository.existsById(id)) {
            chatRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Agregar un participante a un chat
    public ChatParticipant addParticipantToChat(UUID chatId, UUID userId, boolean isAdmin) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat no encontrado"));

        ChatParticipant participant = new ChatParticipant();
        participant.setChat(chat);
        participant.setUser(new User(userId)); // Supone que tienes un constructor en User que acepte UUID
        participant.setAdmin(isAdmin);

        return chatParticipantRepository.save(participant);
    }

    // Obtener participantes de un chat
    public List<ChatParticipant> getParticipants(UUID chatId) {
        return chatParticipantRepository.findByChat_Id(chatId);
    }
}
