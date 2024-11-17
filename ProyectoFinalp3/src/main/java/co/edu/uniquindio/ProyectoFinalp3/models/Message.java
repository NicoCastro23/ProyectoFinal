package co.edu.uniquindio.ProyectoFinalp3.models;

import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat; // Relación con el chat

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Autor del mensaje

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private User receiver; // Relación con el destinatario (puede ser null para mensajes de grupo)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Constructor vacío
    public Message() {
        this.createdAt = LocalDateTime.now();
    }
}
