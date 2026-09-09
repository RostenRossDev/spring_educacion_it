package EducacionIt.web.entities;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
    private Long id;
    private String nombre;
    private int edad;
    private Direccion direccion;
    private String telefono;
    private String email;
}
