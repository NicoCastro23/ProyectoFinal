package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import co.edu.uniquindio.ProyectoFinalp3.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el producto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
public ResponseEntity<?> getProductById(@PathVariable Long id) {
    Optional<Product> product = productService.getProductById(id);
    
    if (product.isPresent()) {
        return ResponseEntity.ok(product.get());
    } else {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Producto no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado o no se pudo actualizar");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.ok("Producto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
    }

    // Endpoint adicional para obtener productos por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getProductsByUserId(@PathVariable Long userId) {
        List<Product> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    // Endpoint adicional para obtener productos por categoría
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    // Endpoint adicional para obtener productos activos
    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> products = productService.getActiveProducts("ACTIVE");
        return ResponseEntity.ok(products);
    }
}
