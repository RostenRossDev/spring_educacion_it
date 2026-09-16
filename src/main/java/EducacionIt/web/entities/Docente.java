package EducacionIt.web.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "EMPLEADO_FK", referencedColumnName = "id")
    private Empleado empleado;

    @ManyToMany(mappedBy = "docentes", fetch = FetchType.LAZY)
    private List<Curso> cursos;
}
