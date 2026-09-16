package EducacionIt.web.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PERSONA_FK", referencedColumnName = "id")
    private Persona personaFK;

    @Column(nullable = false)
    private Integer legajo;

    @Column(name = "FECHA_INGRESO")
    private LocalDateTime fechaIngreso;

}
