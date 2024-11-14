package co.edu.uniquindio.ProyectoFinalp3.dto;

import java.util.UUID;

public class ProductLikeDto {
    private UUID id;
    private UUID productId;

    public ProductLikeDto(UUID id, UUID productId) {
        this.id = id;
        this.productId = productId;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
