package co.edu.uniquindio.ProyectoFinalp3.services;

import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import co.edu.uniquindio.ProyectoFinalp3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Método para crear un nuevo producto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Método para obtener todos los productos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Método para obtener un producto por su ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Método para actualizar un producto existente
    public Product updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }
        return null;
    }

    // Método para eliminar un producto por su ID
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método adicional para obtener productos por usuario
    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    // Método adicional para obtener productos por categoría
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // Método adicional para obtener productos activos
    public List<Product> getActiveProducts(String status) {
        return productRepository.findByStatus(status);
    }
}
