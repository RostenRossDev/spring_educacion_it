package EducacionIt.web.dto;

import EducacionIt.web.entities.Curso;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PersonaDto(
        @Schema(description = "Nombre de  la persona", example = "Nestor", maxLength = 50)
        String nombre, String apellido,
        int edad, String direccion,
        String telefono,
        String email
) {

}
