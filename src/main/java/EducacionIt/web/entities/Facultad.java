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
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nombre;

    @OneToMany(mappedBy = "facultad", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Carrera> carreras;
}
