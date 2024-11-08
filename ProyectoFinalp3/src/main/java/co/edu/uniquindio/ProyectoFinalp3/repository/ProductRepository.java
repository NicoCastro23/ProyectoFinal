package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Encuentra todos los productos de un usuario específico
    List<Product> findByUserId(Long userId);

    // Encuentra todos los productos por categoría
    List<Product> findByCategory(String category);

    // Encuentra todos los productos activos
    List<Product> findByStatus(String status);
}