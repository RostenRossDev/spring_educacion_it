package EducacionIt.web.services;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repository){
        this.repository = repository;
    }

    public List<Persona> all(){
        return repository.findAll();
    }

    public Persona findByid(Long id) {
        return repository.findById(id).get();
    }

    public Persona save(Persona p){
        return repository.save(p);
    }

    public void deleteById(Long id) {
       repository.deleteById(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
