package EducacionIt.web.repositories;

import EducacionIt.web.entities.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersonaRepository {
    private List<Persona> personas = new ArrayList<>();


    public List<Persona> all(){
        return this.personas;
    }

    public Persona findByid(Long id) {
        return personas.stream().filter(p -> p.getId() == id).toList().getFirst();
    }

    public Persona save(Persona p){
        personas.add(p);
        return p;
    }

    public void deleteById(Long id) {
        int index = 0;
        while (personas.get(index).getId() != id){
            index++;
        }
        personas.remove(index);
    }
}
