package EducacionIt.web.controllers;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import EducacionIt.web.services.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController("/persona")  //      ===> DELETE /25/resistencia/argentina
                            //       ===> DELETE /persona
public class PersonaController {

    private final PersonaService service;

    public PersonaController(){
        final PersonaRepository  repo = new PersonaRepository();
        this.service = new PersonaService(repo);
    }


    @GetMapping()
    public ResponseEntity<?> all(){
        List<Persona> personas = service.all();
        return new ResponseEntity(personas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        List<Persona> personas = service.all();
        return new ResponseEntity(personas, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll(){
        //Imagien que eliminamos todos.
        return new ResponseEntity("Se elininaron todos los regisotro",HttpStatus.OK);
    }

//    @PostMapping()
//    public ResponseEntity<?> save(){
//        List<Persona> personas = service.all();
//        return new ResponseEntity(personas, HttpStatus.OK);
//    }
//
//    @GetMapping()
//    public ResponseEntity<?> findById(){
//        List<Persona> personas = service.all();
//        return new ResponseEntity(personas, HttpStatus.OK);
//    }
}
