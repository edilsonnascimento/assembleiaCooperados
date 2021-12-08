package br.org.enascimento.assembleiacooperados.write.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(properties = "test.dataset=WritePautaControllerIT")
class WritePautaControllerIT extends IntegrationHelper {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void GIVEN_ValidData_MUST_CreatePauta() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters();

        var payload =
                """
                   {
                      "uuid": "%s",
                      "titulo": "%s",
                      "descricao": "%s"
                   }
                """.formatted(uuid, titulo, descricao);
        //when
        mockMvc
                .perform(post("/v1/pautas/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        //then
        mockMvc
                .perform(get("/v1/pautas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid",
                        containsInRelativeOrder("3731c747-ea27-42e5-a52b-1dfbfa9617db", "6d9db741-ef57-4d5a-ac0f-34f68fb0ab5e", uuid)))
                .andExpect(jsonPath("$[*].titulo",
                        containsInRelativeOrder("PRIMEIRO-TITULO", "SEGUNDO-TITULO", titulo)))
                .andExpect(jsonPath("$[*].descricao",
                        containsInRelativeOrder("PRIMEIRA-DESCICAO", "SEGUNDA-DESCICAO", descricao)));
    }

    @Test
    void GIVEN_InvalidData_MUST_ReturError() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.lorem().characters();
        var descricao = faker.lorem().characters();

        var payload =
                """
                   {
                      "uuid": "%s",
                      "titulo": "%s",
                      "descricao": "%s"
                   }
                """.formatted(uuid, titulo, descricao);
        //when
        mockMvc
                .perform(post("/v1/pautas/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

    }

    @Test
    void GIVEN_ValidData_MUST_UpdatePauta() throws Exception {
        //given
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters();
        var uuid = fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");

        var payload =
                """
                   {
                      "titulo": "%s",
                      "descricao": "%s"
                   }
                """.formatted(titulo, descricao);
        //when
        mockMvc
                .perform(put("/v1/pautas/{uuid}", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk());

        //then
        mockMvc
                .perform(get("/v1/pautas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder(uuid)))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder(titulo)))
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder(descricao)));
    }
}