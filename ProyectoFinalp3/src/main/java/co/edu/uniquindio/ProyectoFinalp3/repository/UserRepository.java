package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Aquí se pueden definir consultas personalizadas si es necesario
    User findByEmail(String email); 
    
    // Verificar si el correo electrónico ya existe
    boolean existsByEmail(String email);

}