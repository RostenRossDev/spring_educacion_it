package EducacionIt.web.unitTesting;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import EducacionIt.web.repositories.UserRepository;
import EducacionIt.web.services.PersonaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class) //No carga el contexto de spring x eso se usa en unitTesting y no en testing de integracion
class PersonaServiceTest {

    @Mock
    private PersonaRepository personaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonaService personaService;

    // ---------- GUARDAR ----------

    @Test
    void deberiaGuardarPersona() {
        // Given
        var persona = new Persona(null, "Nestor", "Costantini", 35, "Calle Falsa 123", "54545454");
        var guardada = new Persona(1L, "Nestor", "Costantini", 35, "Calle Falsa 123", "54545454");
        when(personaRepository.save(any(Persona.class))).thenReturn(guardada);

        // When
        var resultado = personaService.save(persona);

        // Then
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Nestor");
        verify(personaRepository).save(persona);
    }

}