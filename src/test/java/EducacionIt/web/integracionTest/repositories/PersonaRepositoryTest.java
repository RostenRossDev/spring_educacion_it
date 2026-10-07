package EducacionIt.web.integracionTest.repositories;

import EducacionIt.web.entities.Persona;
import EducacionIt.web.repositories.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplaza el datasource del application-test.yml
@Testcontainers
@ActiveProfiles("test")  // Indica que archivo de configuracion usar durante la ejecuccion del test
public class PersonaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonaRepository personaRepository;

    @Test
    void deberiaEncontrarPersonaPorApellido() {
        var p1 = new Persona(null, "Nestor", "Costantini", 35, "asdasdas", "54545454");
        var p2 = new Persona(null, "Matias", "Costantini", 34, "fgliii", "788567567");
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();

        var resultado = personaRepository.findByApellido("Costantini");

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Nestor");
    }
}
