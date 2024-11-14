package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.Contact;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    // Endpoint para añadir un contacto usando UUID en el body
    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Map<String, UUID> body) {
        UUID userId = body.get("userId");
        UUID contactUserId = body.get("contactUserId");
        Contact contact = contactService.addContact(userId, contactUserId);
        return ResponseEntity.ok(contact);
    }

    // Endpoint para obtener la lista de contactos de un usuario específico usando UUID
    @GetMapping("/{userId}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable UUID userId) {
        List<Contact> contacts = contactService.getContacts(userId);
        return ResponseEntity.ok(contacts);
    }

    // Endpoint para eliminar un contacto usando UUID en el body
    @DeleteMapping
    public ResponseEntity<Void> removeContact(@RequestBody Map<String, UUID> body) {
        UUID userId = body.get("userId");
        UUID contactUserId = body.get("contactUserId");
        contactService.removeContact(userId, contactUserId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener sugerencias de contactos de un usuario usando UUID
    @GetMapping("/suggestions/{userId}")
    public ResponseEntity<List<User>> getSuggestedContacts(@PathVariable UUID userId) {
        List<User> suggestedContacts = contactService.getSuggestedContacts(userId);
        return ResponseEntity.ok(suggestedContacts);
    }
}
