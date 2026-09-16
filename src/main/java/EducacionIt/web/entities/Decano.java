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
public class Decano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "EMPLEADO_FK", referencedColumnName = "id")
    private Empleado empleado;

    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaInicio;
}
