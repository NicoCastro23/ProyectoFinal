package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // Encuentra todas las órdenes de un usuario específico
    List<Order> findByUserId(UUID userId);

    // Encuentra todas las órdenes por estado
    List<Order> findByStatus(String status);
}