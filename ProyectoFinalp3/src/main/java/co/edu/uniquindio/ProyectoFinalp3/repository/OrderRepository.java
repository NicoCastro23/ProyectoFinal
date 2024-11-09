package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // Encuentra todas las órdenes de un usuario específico
    List<Order> findByUserId(String userId);

    // Encuentra todas las órdenes por estado
    List<Order> findByStatus(String status);
}