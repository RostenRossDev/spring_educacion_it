package EducacionIt.web.services;

import EducacionIt.web.repositories.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService {
    private final PersonaRepository repository;

    public DireccionService(PersonaRepository repository){
        this.repository = null;
    }

    public void deleteById(Long id) {
        return;
    }
}
