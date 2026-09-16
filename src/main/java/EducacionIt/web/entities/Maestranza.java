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
public class Maestranza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "EMPLEADO_ID", referencedColumnName = "id")
    private Empleado empleado;

    @Column(name = "HORA_INGRESO", length = 20, nullable = false)
    private String horaIngreso;

    @Column(name = "HORA_SALIDA", length = 20, nullable = false)
    private String horaSalida;
}
