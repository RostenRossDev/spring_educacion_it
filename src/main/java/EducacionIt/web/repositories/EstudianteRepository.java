package EducacionIt.web.repositories;

import EducacionIt.web.entities.Estudiante;
import EducacionIt.web.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

}
