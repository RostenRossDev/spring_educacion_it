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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String fecha;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "curso_docente",
            joinColumns        = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "docente_id")
    )
    private List<Docente> docentes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MATERIA_FK", nullable = false)
    private Materia maeria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "curso_estudiante",
            joinColumns        = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private List<Estudiante> estudiante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CARRERA_FK", nullable = false)
    private Carrera carrera;

    @OneToOne
    @JoinColumn(name = "AULA_FK", referencedColumnName = "id")
    private Aula aula;
}
