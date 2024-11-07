package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product_likes")
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor vacío
    public ProductLike() {}

    // Constructor con parámetros
    public ProductLike(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    // Getter y Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
