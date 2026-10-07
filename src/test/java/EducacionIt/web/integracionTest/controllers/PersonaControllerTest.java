package EducacionIt.web.integracionTest.controllers;

import EducacionIt.web.controllers.PersonaController;
import EducacionIt.web.entities.Persona;
import EducacionIt.web.security.IUserService;
import EducacionIt.web.security.JwtUtil;
import EducacionIt.web.services.PersonaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;


//Verbos HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

// MockMvcResultMatchers
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Matchers. Estáticos
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(PersonaController.class) // Carga parte del contexto de spring por eso se usa en test de integracion y no en unitTest
public class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonaService personaService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private IUserService userDetailsService;  // si el filtro también lo usa

    @Test
    void deberiaListarPersonas() throws Exception {
        when(personaService.all()).thenReturn(List.of(new  Persona(1L, "Nestor", "Costantini", 35, "...", "...")));

        var resultado = mockMvc.perform(get("/persona"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))       // Hamcrest
                .andReturn();

        // AssertJ para el resto
        var json = resultado.getResponse().getContentAsString();
        assertThat(json).contains("Costantini");
    }
}
