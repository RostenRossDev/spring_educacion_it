package EducacionIt.web;

import EducacionIt.web.entities.Aula;
import EducacionIt.web.entities.Role;
import EducacionIt.web.services.PersonaService;
import EducacionIt.web.validator.NotVoidValidator;

public class Prueba {

    static void main() {
        Role r = new Role(null, "DePrueba", null, null);

        r.setDescription("uN parametro de juguete", 1L);
    }
}
