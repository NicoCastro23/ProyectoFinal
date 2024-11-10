package co.edu.uniquindio.ProyectoFinalp3.repository;

import co.edu.uniquindio.ProyectoFinalp3.models.Contact;
import co.edu.uniquindio.ProyectoFinalp3.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    // Encuentra todos los contactos de un usuario específico
    List<Contact> findByUser(User user);

    // Encuentra si ya existe una relación de contacto entre dos usuarios
    Optional<Contact> findByUserAndContactUser(User user, User contactUser);

    // Elimina un contacto específico entre dos usuarios
    void delete(Contact contact);
}
