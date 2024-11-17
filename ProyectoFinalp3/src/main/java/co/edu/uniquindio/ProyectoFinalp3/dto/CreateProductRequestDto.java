package co.edu.uniquindio.ProyectoFinalp3.dto;

import java.util.UUID;

import co.edu.uniquindio.ProyectoFinalp3.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductRequestDto {
    private UUID userId;
    private Product product;
}
