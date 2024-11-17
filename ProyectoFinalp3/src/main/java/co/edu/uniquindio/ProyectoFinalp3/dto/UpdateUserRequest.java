package co.edu.uniquindio.ProyectoFinalp3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String cedula;
}
