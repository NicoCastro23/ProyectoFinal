package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    // Encuentra todos los pagos de un usuario específico
    List<Payment> findByUserId(String userId);

    // Encuentra todos los pagos por estado
    List<Payment> findByStatus(String status);
}
