package EducacionIt.web.services;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;

import java.util.List;

public class PersonaService {

    private final PersonaRepository repository;

    public PersonaService(PersonaRepository repository){
        this.repository = repository;
    }


    public List<Persona> all(){
        return repository.all();
    }

    public Persona findByid(Long id) {
        return repository.findByid(id);
    }

    public Persona save(Persona p){
        return save(p);
    }

    public void deleteById(Long id) {
       repository.deleteById(id);
    }

}
