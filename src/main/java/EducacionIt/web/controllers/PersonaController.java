package EducacionIt.web.controllers;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import EducacionIt.web.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

// education-it.com -> dns => 159.347.29
//159.347.29/persona
//education-it.com/persona   => GET
//education-it.com/persona/58   => DELETE
//education-it.com/persona   => DELETE
@RestController("/persona")  //      ===> DELETE /25/resistencia/argentina
                             //       ===> DELETE /persona
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping()
    public ResponseEntity<?> all(){
        List<Persona> personas = personaService.all();
        return new ResponseEntity(personas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        Persona persona = personaService.findByid(id);
        return new ResponseEntity(persona, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll(){
        //Imagien que eliminamos todos.
        personaService.deleteAll();
        return new ResponseEntity("Se eliminaron todos los registros",HttpStatus.OK);
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
