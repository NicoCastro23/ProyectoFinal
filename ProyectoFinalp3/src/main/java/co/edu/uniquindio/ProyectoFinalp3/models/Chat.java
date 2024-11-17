package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name; // Nombre del chat, útil para grupos

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relación con los participantes del chat
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatParticipant> chatParticipants;

    // Relación con el primer usuario
    @ManyToOne(optional = false)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    // Relación con el segundo usuario
    @ManyToOne(optional = false)
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    /**
     * Método que se ejecuta antes de persistir el objeto para garantizar
     * que `createdAt` tenga un valor.
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    /**
     * Constructor para inicializar con usuarios y nombre.
     */
    public Chat(String name, User user1, User user2) {
        this.name = name;
        this.user1 = user1;
        this.user2 = user2;
        this.createdAt = LocalDateTime.now();
    }
}
