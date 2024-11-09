package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 10, max = 15) // Adjust size based on your phone number length requirements
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format") // A simple pattern for phone
                                                                                     // number
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL) // Optional: Add cascade if you want automatic cascading operations
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "contact_user_id")
    private User contactUser;

    // Constructor vacío
    public Contact() {
    }

    // Constructor con parámetros
    public Contact(String name, String phoneNumber, User user) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    // Getter y Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Contact{id=" + id + ", name='" + name + "', phoneNumber='" + phoneNumber + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}