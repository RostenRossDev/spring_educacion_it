package EducacionIt.web.controllers;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.services.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Persona", description = "API para gestión de personas")
@RestController
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;


    // #############################  POST  #############################

    /* POST -> /persona/     -> no va a fallar  */
    @Operation(
            summary = "Crear u nregistro pesona",
            description = "Crea una nueva entrada en la tabla de personas."
    )
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Persona persona){
        System.out.println("Ingreso al controller para crear pesonas: " + persona);
        Persona newPersona = personaService.save(persona);
        return new ResponseEntity<>(newPersona, HttpStatus.OK);
    }


    // ############################# GET #############################
    /* GET -> /personas/     -> No va a fallar porque matchea la solicitud con el mappeo descripto */
    @GetMapping
    public ResponseEntity<?> all(){
        List<Persona> personas = personaService.all();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    /* GET -> /personas/asdasdas     -> va a fallar xq si bien va a ejecutar el metodo, este no va a poder convetir el path en un LONG */
    /* GET -> /personas/63           ->No va a fallar xq si va a poder convertir el PATH en un long */
    @Operation(
            summary = "Obtener persona por ID",
            description = "Retorna los datos de una persona según su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById( @Parameter(name = "id", description = "ID de la persona a buscar", example = "1") @PathVariable("id") Long id){
        Persona persona = personaService.findById(id);
        return new ResponseEntity<>(persona, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByLastnameOrEmail(@RequestParam(name = "lastname", required = false) String apellido,
                                            @RequestParam(name = "correo", required = false) String email){
        return personaService.findByEmailOrLastname(apellido, email);
    }


    //############################# DELETE #############################

    /* DELETE -> /personas/asdasdas     -> va a fallar xq si bien va a ejecutar el metodo, este no va a poder convetir el path en un LONG */
    /* DELETE -> /personas/63           -> No va a fallar xq si va a poder convertir el PATH en un long */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persona eliminada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete( @Parameter(name = "id", description = "ID de la persona a eliminar", example = "1") @PathVariable("id") Long id){
        personaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // #############################  PUT / UPDATEDE  #############################

    /* UPDATE -> /personas/     -> no va a fallar  */
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Persona persona){
        Persona updated = personaService.update(persona);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
