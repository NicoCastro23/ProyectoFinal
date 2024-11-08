package co.edu.uniquindio.ProyectoFinalp3.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product_comments")
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String commentText;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor vacío
    public ProductComment() {}

    // Constructor con parámetros
    public ProductComment(String commentText, Product product, User user) {
        this.commentText = commentText;
        this.product = product;
        this.user = user;
    }

    // Getter y Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}