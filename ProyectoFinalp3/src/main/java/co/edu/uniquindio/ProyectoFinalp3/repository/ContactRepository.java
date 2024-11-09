package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    // Encuentra todos los contactos de un usuario
    List<Contact> findByUserId(UUID userId);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId AND c.contactUser.id = :contactUserId")
    Optional<Contact> findByUserIdAndContactUserId(@Param("userId") UUID userId,
            @Param("contactUserId") UUID contactUserId);

}