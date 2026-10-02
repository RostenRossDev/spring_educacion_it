package EducacionIt.web.services;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.entities.Role;
import EducacionIt.web.exceptions.BadArgtumentException;
import EducacionIt.web.exceptions.RegisterNotFoundException;
import EducacionIt.web.repositories.PersonaRepository;
import EducacionIt.web.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public Role finByName(String name){
        return repository.findByName(name).getFirst();
    }

}
