package EducacionIt.web.repositories;

import EducacionIt.web.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    List<Persona> findByApellido(String apellido);
    Persona findByEmail(String email);


//    @Query("select p from Persona p inner join Empleado em on p.id = em.personaFK inner join Estudiante es on p.id = es.personaFK")
//    List<Persona> encontrarPersonaEmpleadoQueFueOesEstudiante();

//    @Query(value = """
//            select DISTINCT p.id, p.nombre, p.apellido, p.edad, p.direccion from pesonas p
//                            inner join empleado em on p.id = em.persona_fk
//                            inner join estudiante es on p.id = es.persona_fk""", nativeQuery = true)
//    List<Persona> encontrarPersonaEmpleadoQueEsEstudianteNative();
}
