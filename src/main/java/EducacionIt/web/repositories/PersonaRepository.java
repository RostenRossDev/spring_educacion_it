package EducacionIt.web.repositories;

import EducacionIt.web.entities.Persona;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

}
