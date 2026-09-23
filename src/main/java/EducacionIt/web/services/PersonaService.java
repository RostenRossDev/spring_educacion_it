package EducacionIt.web.services;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.exceptions.BadArgtumentException;
import EducacionIt.web.exceptions.RegisterNotFoundException;
import EducacionIt.web.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository repository;


    public List<Persona> all(){
        return repository.findAll();
    }

    public Persona findById(Long id) {
        return repository.findById(id).get();
    }

    public Persona findByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Persona> findByLastName(String apellido){
        return repository.findByApellido(apellido);
    }

    @Transactional
    public Persona save(Persona persona) {
        System.out.println("Ingreso al service para crear pesonas");
        return repository.save(persona);
    }

    @Transactional
    public Persona update(Persona persona) {
        Persona toUpdate = repository.findById(persona.getId()).get();
        if (toUpdate == null) throw new RegisterNotFoundException(String.format("Persona con id %s no encontrado", persona.getId().toString()));
        return repository.save(persona);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> findByEmailOrLastname (String apellido, String email) {

        if (email == null && apellido != null) {
            List<Persona> personas = findByLastName(apellido);
            return new ResponseEntity<>(personas, HttpStatus.OK);
        }

        if (email != null && apellido == null) {
            Persona persona = findByEmail(email);
            return new ResponseEntity<>(persona, HttpStatus.OK);
        }

        String message = "Al menos uno de los siguientes parametros no debe ser nulo {lastname, correo}";
        throw new BadArgtumentException(message);
    }


}
