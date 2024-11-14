package co.edu.uniquindio.ProyectoFinalp3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import co.edu.uniquindio.ProyectoFinalp3.enums.RoleEnum;
import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.services.JwtService;
import co.edu.uniquindio.ProyectoFinalp3.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam String role) {
        // Convertir el String a RoleEnum
        try {
            RoleEnum roleEnum = RoleEnum.valueOf(role.toUpperCase()); // Convierte el String a RoleEnum
            List<User> users = userService.findAll(roleEnum); // Pasar RoleEnum al servicio
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            // Manejo del error si el rol no es válido
            return ResponseEntity.badRequest().body(null); // Puedes devolver un mensaje adecuado
        }
    }

    // Endpoint para buscar usuarios por nombre de usuario
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByUsername(@RequestParam String username) {
        List<User> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    // Nuevo endpoint para buscar un usuario por su ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Autowired
    private JwtService jwtService;

    // Endpoint para actualizar la información del usuario autenticado
    @PatchMapping("/{userId}/update")
    public ResponseEntity<String> updateUserInfo(
            @PathVariable UUID userId,
            @RequestBody User updatedUserInfo,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Verifica si el encabezado tiene el token en el formato correcto
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("No autorizado: el token está ausente o es incorrecto.");
        }

        // Extrae el token eliminando el prefijo "Bearer "
        String token = authorizationHeader.substring(7);

        // Obtener el ID del usuario autenticado desde el token
        UUID authenticatedUserId;
        try {
            authenticatedUserId = jwtService.extractUserId(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado.");
        }

        // Verifica si el userId del token coincide con el userId en el path
        if (!userId.equals(authenticatedUserId)) {
            return ResponseEntity.status(403).body("No tienes permiso para actualizar esta información.");
        }

        // Intenta actualizar la información del usuario
        boolean updated = userService.updateUserInfo(userId, updatedUserInfo);
        if (updated) {
            return ResponseEntity.ok("Información actualizada exitosamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
}