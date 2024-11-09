package co.edu.uniquindio.ProyectoFinalp3.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // Genera el JWT
    public String generateToken(String userId) {
        // Usamos el algoritmo HMAC256 para firmar el token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // Generamos el token
        return JWT.create()
                .withSubject(userId) // Se utiliza el correo o username como 'subject'
                .withIssuedAt(new Date()) // Fecha de emisión
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime)) // Fecha de expiración
                .sign(algorithm); // Firmamos el token con el secreto
    }

    // Extrae el username (o correo) del JWT
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject(); // Devuelve el 'subject', que es el username o correo
    }

    // Valida si el token ha expirado
    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    // Extrae la fecha de expiración del token
    public Date extractExpirationDate(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getExpiresAt(); // Devuelve la fecha de expiración
    }

    // Decodifica el JWT para obtener su contenido
    private DecodedJWT decodeToken(String token) {
        // Usamos el mismo algoritmo para verificar y decodificar el token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .build()
                .verify(token); // Verifica y decodifica el token
    }
}