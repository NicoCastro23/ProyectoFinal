package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;
import java.util.List;

import co.edu.uniquindio.ProyectoFinalp3.enums.RoleEnum;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;
    private String firstName;
    private String lastName;
    private String cedula;
    private String address;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    // Inicializamos las listas para evitar NullPointerException
    @OneToMany(mappedBy = "user1")
    private List<Chat> chatsAsUser1; // Relación como user1

    @OneToMany(mappedBy = "user2")
    private List<Chat> chatsAsUser2; // Relación como user2

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    // Constructor vacío
    public User() {}

    // Constructor con parámetros
    public User(String username, String password, String firstName, String lastName, String cedula, String address, RoleEnum role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cedula = cedula;
        this.address = address;
        this.role = role;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public RoleEnum getRole() { return role; }
    public void setRole(RoleEnum role) { this.role = role; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }

    public List<Chat> getChatsAsUser1() { return chatsAsUser1; }
    public void setChatsAsUser1(List<Chat> chatsAsUser1) { this.chatsAsUser1 = chatsAsUser1; }

    public List<Chat> getChatsAsUser2() { return chatsAsUser2; }
    public void setChatsAsUser2(List<Chat> chatsAsUser2) { this.chatsAsUser2 = chatsAsUser2; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}