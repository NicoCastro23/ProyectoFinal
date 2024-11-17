package co.edu.uniquindio.ProyectoFinalp3.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ProductLikeDto {
    private UUID id;
    private UUID productId;

}
