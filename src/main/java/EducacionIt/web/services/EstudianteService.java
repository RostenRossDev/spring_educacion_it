package EducacionIt.web.services;

import EducacionIt.web.dto.EstudianteDto;
import EducacionIt.web.entities.Estudiante;
import EducacionIt.web.entities.Persona;
import EducacionIt.web.exceptions.RegisterNotFoundException;
import EducacionIt.web.repositories.EstudianteRepository;
import EducacionIt.web.validator.NotVoid;
import EducacionIt.web.validator.NotVoidValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository repository;


    public List<Estudiante> all(){
        return repository.findAll();
    }

    public EstudianteDto findById(Long id) {

        Estudiante estudiante = repository.findById(id).get();
        Persona p = estudiante.getPersonaFK();
        return EstudianteDto.builder()
                .cursos(estudiante.getCursos())
                .edad(p.getEdad())
                .nombre(p.getNombre())
                .legajo(estudiante.getLegajo())
                .apellido(p.getApellido())
                .telefono(p.getTelefono())
                .direccion(p.getDireccion())
                .build();

       // return new EstudianteDto(estudiante.getLegajo(), p.getNombre(), p.getApellido(), p.getEdad(), p.getDireccion(), p.getTelefono(), estudiante.getCursos());
    }

    @Transactional
    public Estudiante save(Estudiante estudiante) {
        System.out.println("Ingreso al service para crear pesonas");
        return repository.save(estudiante);
    }

    @Transactional
    public Estudiante update(@NotVoid Estudiante estudiante) throws NoSuchMethodException {
        Method metodo = this.getClass().getMethod("update", String.class, Long.class);
        Object[] argumentos = new Object[]{ estudiante };
        NotVoidValidator.validateParameters(metodo, argumentos);


        Estudiante toUpdate = repository.findById(estudiante.getId()).get();
        if (toUpdate == null) throw new RegisterNotFoundException(String.format("Persona con id %s no encontrado", estudiante.getId().toString()));
        return repository.save(estudiante);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
