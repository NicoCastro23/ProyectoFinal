package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    // Encuentra todos los mensajes de un chat específico
    List<Message> findByChat_Id(UUID chatId);

    // Encuentra los últimos N mensajes de un chat, ordenados por fecha
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.createdAt DESC")
    List<Message> findRecentMessages(UUID chatId);

    // Encuentra mensajes enviados por un usuario específico en un chat
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId AND m.user.id = :userId")
    List<Message> findMessagesByUserInChat(UUID chatId, UUID userId);

    // Encuentra mensajes privados entre dos usuarios
    @Query("SELECT m FROM Message m WHERE (m.user.id = :userId1 AND m.receiver.id = :userId2) OR (m.user.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.createdAt ASC")
    List<Message> findMessagesBetweenUsers(UUID userId1, UUID userId2);
}
