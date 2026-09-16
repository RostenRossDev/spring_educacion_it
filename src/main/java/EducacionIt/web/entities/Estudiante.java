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
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "PERSONA_FK", referencedColumnName = "id")
    private Persona personaFK;

    @Column(nullable = false)
    private Integer legajo;

    @ManyToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List<Curso> cursos;
}
