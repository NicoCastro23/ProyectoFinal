package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.dto.CreateProductRequestDto;
import co.edu.uniquindio.ProyectoFinalp3.enums.ProductStatus;
import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import co.edu.uniquindio.ProyectoFinalp3.models.ProductComment;
import co.edu.uniquindio.ProyectoFinalp3.services.ProductCommentService;
import co.edu.uniquindio.ProyectoFinalp3.services.ProductLikeService;
import co.edu.uniquindio.ProyectoFinalp3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
     @Autowired
    private ProductLikeService productLikeService; 

    @Autowired
    private ProductCommentService productCommentService; 

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequestDto request) {
    try {
        // Extraer el producto y el userId del request
        Product createdProduct = productService.createProduct(request.getProduct(), request.getUserId());
        return ResponseEntity.ok(createdProduct);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el producto: " + e.getMessage());
    }
}

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.getProductById(id);
        return product.isPresent() ? ResponseEntity.ok(product.get()) 
                                   : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct)
                                      : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado o no se pudo actualizar");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id) ? ResponseEntity.ok("Producto eliminado correctamente")
                                                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(productService.getProductsByUserId(userId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        return ResponseEntity.ok(productService.getActiveProducts(ProductStatus.ACTIVE));  
    }
    // Nueva funcionalidad: Endpoint para agregar un comentario a un producto
    @PostMapping("/{productId}/comments")
    public ResponseEntity<ProductComment> addComment(
            @PathVariable UUID productId,
            @RequestParam UUID userId,
            @RequestParam String commentText) {

        try {
            ProductComment comment = productCommentService.addCommentToProduct(productId, userId, commentText);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Nueva funcionalidad: Endpoint para obtener comentarios de un producto
    @GetMapping("/{productId}/comments")
    public ResponseEntity<List<ProductComment>> getCommentsByProduct(@PathVariable UUID productId) {
        List<ProductComment> comments = productCommentService.getCommentsByProduct(productId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{productId}/likes")
    public ResponseEntity<String> addLike(
            @PathVariable UUID productId,
            @RequestBody Map<String, String> requestBody) {
    
        String userIdStr = requestBody.get("userId");
        if (userIdStr == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El userId es requerido.");
        }
    
        UUID userId;
        try {
            userId = UUID.fromString(userIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El userId no tiene un formato válido.");
        }
    
        boolean liked = productLikeService.addLikeToProduct(productId, userId);
        if (liked) {
            return ResponseEntity.ok("Like agregado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya ha dado like a este producto.");
        }
    }
    


    // Endpoint para obtener la cantidad de "likes" de un producto
    @GetMapping("/{productId}/likes/count")
    public ResponseEntity<Long> getProductLikesCount(@PathVariable UUID productId) {
        long likeCount = productLikeService.getLikesCountByProduct(productId);
        return ResponseEntity.ok(likeCount);
    }
}