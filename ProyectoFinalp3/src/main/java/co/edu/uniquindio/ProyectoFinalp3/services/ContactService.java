package co.edu.uniquindio.ProyectoFinalp3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.ProyectoFinalp3.models.Contact;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.repository.ContactRepository;
import co.edu.uniquindio.ProyectoFinalp3.repository.UserRepository;

@Service
public class ContactService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ContactRepository contactRepository;

    public void createContactForUser(Long userId, String name, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Contact contact = new Contact(name, phoneNumber, user);
        contactRepository.save(contact);
    }
}
