package EducacionIt.web.puntoApunto;

import EducacionIt.web.dto.PersonaDto;
import EducacionIt.web.entities.Persona;
import EducacionIt.web.security.IUserService;
import EducacionIt.web.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
@AutoConfigureRestTestClient
class PersonaApiIT {

    @Autowired
    private RestTestClient restClient;

    @Autowired
    private JwtUtil jwtUtil; // Tu clase generadora de tokens

    @Autowired
    private IUserService userDetailsService; // Para cargar el usuario

    // ---------- FLUJO COMPLETO: CREAR → BUSCAR → ACTUALIZAR → ELIMINAR ----------

    @Test
    void deberiaHacerElFlujoCompletoDeUnaPersona() {
        // ============ 1. CREAR (POST) ============
        String token ="Bearer ".concat(JwtUtil.generarTokenParaUsuario("costantini.nestor.m@gmail.com", userDetailsService));

        var request = new Persona(null, "Nestor", "Costantini", 35, "Calle Falsa 123", "54545454");

        var postResponse = restClient.post()
                .uri("/persona")
                .header("authorization", token)
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Persona.class)
                .returnResult();

        assertThat(postResponse.getStatus()).isEqualTo(HttpStatus.CREATED);

        var creada = postResponse.getResponseBody();
        System.out.println(creada.toString());
        assertThat(creada).isNotNull();
        assertThat(creada.getId()).isNotNull();
        assertThat(creada.getNombre()).isEqualTo("Nestor");
        assertThat(creada.getApellido()).isEqualTo("Costantini");

        // ============ 2. BUSCAR (GET) ============
        Long id = creada.getId();
        var getResponse = restClient.get()
                .uri("/persona/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Persona.class)
                .returnResult();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getResponseBody()).isNotNull();
        assertThat(getResponse.getResponseBody().getNombre()).isEqualTo("Nestor");
    }


}