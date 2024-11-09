package co.edu.uniquindio.ProyectoFinalp3.models;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1; // Primer usuario en el chat

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2; // Segundo usuario en el chat

    // Constructor vacío
    public Chat() {}

    // Constructor con parámetros
    public Chat(String message, User user1, User user2) {
        this.message = message;
        this.user1 = user1;
        this.user2 = user2;
    }

    // Getter y Setter
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }

    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }
}