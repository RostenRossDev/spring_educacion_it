package EducacionIt.web.controllers;

import EducacionIt.web.dto.EstudianteDto;
import EducacionIt.web.entities.Estudiante;
import EducacionIt.web.entities.Persona;
import EducacionIt.web.services.EstudianteService;
import EducacionIt.web.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/estudiante")
public class AlumnosController {

    @Autowired
    private EstudianteService service;


    // #############################  POST  #############################

    /* POST -> /estudiante/     -> no va a fallar  */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Estudiante estudiante){
        System.out.println("Ingreso al controller para crear pesonas: " + estudiante);
        Estudiante newEstudaitne = service.save(estudiante);
        return new ResponseEntity<>(newEstudaitne, HttpStatus.OK);
    }


    // ############################# GET #############################
    /* GET -> /estudiante/     -> No va a fallar porque matchea la solicitud con el mappeo descripto */
    @GetMapping
    public ResponseEntity<?> all(){
        List<Estudiante> estudiantes = service.all();
        return new ResponseEntity<>(estudiantes, HttpStatus.OK);
    }

    /* GET -> /estudiante/asdasdas     -> va a fallar xq si bien va a ejecutar el metodo, este no va a poder convetir el path en un LONG */
    /* GET -> /estudiante/63           ->No va a fallar xq si va a poder convertir el PATH en un long */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
//        EstudianteDto estudiante = service.findById(id);
//        return new ResponseEntity<>(estudiante, HttpStatus.OK);
    }

    //############################# DELETE #############################

    /* DELETE -> /estudiante/asdasdas     -> va a fallar xq si bien va a ejecutar el metodo, este no va a poder convetir el path en un LONG */
    /* DELETE -> /estudiante/63           -> No va a fallar xq si va a poder convertir el PATH en un long */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // #############################  PUT / UPDATEDE  #############################

    /* UPDATE -> /persona/     -> no va a fallar  */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Estudiante estudiante){
        Estudiante updated = service.update(estudiante);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
