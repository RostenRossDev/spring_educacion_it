package EducacionIt.web.integracionTest.services;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import EducacionIt.web.services.PersonaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)   // Habilita Mockito con JUnit 5
class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;   // Dependencia simulada

    @InjectMocks
    private PersonaService personaService;         // El servicio real, con los mocks inyectados

    @Test
    void deberiaGuardarPersona() {
        // Given
        var persona = new Persona(null,"Nestor", "Costantini", 35, "...", "...");
        var guardada = new Persona(1L, "Nestor", "Costantini", 35, "...", "...");
        when(personaRepository.save(any(Persona.class))).thenReturn(guardada);

        // When
        var resultado = personaService.save(persona);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(personaRepository).save(persona);
    }
}