package EducacionIt.web.entities;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PERSONAS")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(length = 8)
    private int edad;

    @Column(length = 50)
    private String direccion;

    @Column(length = 10)
    private String telefono;

    @Column(length = 50, unique = true)
    private String email;
}
