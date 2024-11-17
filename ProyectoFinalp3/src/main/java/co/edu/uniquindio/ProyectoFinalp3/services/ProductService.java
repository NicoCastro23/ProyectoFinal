package co.edu.uniquindio.ProyectoFinalp3.services;

import co.edu.uniquindio.ProyectoFinalp3.enums.ProductStatus;
import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import co.edu.uniquindio.ProyectoFinalp3.repository.UserRepository;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;  // Asegúrate de que esta línea esté aquí
    public Product createProduct(Product product, UUID userId) {
        // Buscar el usuario por UUID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
    
        // Asociar el usuario al producto
        product.setUser(user);
    
        // Guardar el producto en el repositorio
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }
        return null;
    }

    public boolean deleteProduct(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> getProductsByUserId(UUID userId) {
        // Llama al nuevo método del repositorio
        return productRepository.findByUser_Id(userId);
    }
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getActiveProducts(ProductStatus status) {  
        return productRepository.findByStatus(status);
    }
}