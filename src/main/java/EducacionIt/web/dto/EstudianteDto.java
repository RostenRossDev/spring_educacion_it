package EducacionIt.web.dto;

import EducacionIt.web.entities.Curso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EstudianteDto {
    Integer legajo;
    String nombre;
    String apellido;
    int edad;
    String direccion;
    String telefono;
    String email;
    List<Curso> cursos;

}
