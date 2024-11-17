package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "chat_participants")
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean isAdmin; // Indica si este usuario es administrador del chat

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinedAt = LocalDateTime.now(); // Fecha y hora de ingreso

    public ChatParticipant(Chat chat, User user, boolean isAdmin) {
        this.chat = chat;
        this.user = user;
        this.isAdmin = isAdmin;
        this.joinedAt = LocalDateTime.now(); // Inicializar con la fecha y hora actuales
    }
}
