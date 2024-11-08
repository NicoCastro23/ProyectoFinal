package co.edu.uniquindio.ProyectoFinalp3.controllers;

import co.edu.uniquindio.ProyectoFinalp3.models.User;
import co.edu.uniquindio.ProyectoFinalp3.services.JwtService;
import co.edu.uniquindio.ProyectoFinalp3.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    // Login: Autenticación del usuario
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            String token = jwtService.generateToken(user.getEmail());
            response.put("ok", true);
            response.put("token", token);
            return ResponseEntity.ok(response);
        }

        // Si el usuario no existe o las credenciales no son válidas
        response.put("ok", false);
        response.put("msg", "Credenciales inválidas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Register: Registro de un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User registerRequest) {
        Map<String, Object> response = new HashMap<>();

        // Validación básica del email
        if (registerRequest.getEmail() == null || !registerRequest.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            response.put("ok", false);
            response.put("msg", "Email no es válido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Verifica si el correo electrónico ya está registrado
        if (userService.emailExists(registerRequest.getEmail())) {
            response.put("ok", false);
            response.put("msg", "El correo electrónico ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Validación de la contraseña
        if (registerRequest.getPassword() == null || registerRequest.getPassword().length() < 8) {
            response.put("ok", false);
            response.put("msg", "La contraseña debe tener al menos 8 caracteres");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Crea el usuario (asegúrate de que la contraseña esté codificada)
        User newUser = userService.createUser(registerRequest);

        // Genera el JWT para el nuevo usuario
        String token = jwtService.generateToken(newUser.getEmail());

        // Respuesta exitosa
        response.put("ok", true);
        response.put("msg", "Usuario registrado exitosamente");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

}