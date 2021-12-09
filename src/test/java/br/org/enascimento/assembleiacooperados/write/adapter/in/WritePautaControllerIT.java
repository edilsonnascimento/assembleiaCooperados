package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultMatcher;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(properties = "test.dataset=WritePautaControllerIT")
class WritePautaControllerIT extends IntegrationHelper {

    @Test
    void GIVEN_ValidData_MUST_CreatePauta() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters(10);

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
                .andExpect(exists(uuid))
                .andExpect(exists(titulo))
                .andExpect(exists(descricao));
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
        var descricao = faker.lorem().characters(20);
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
                .andExpect(status().isNoContent());

        //then
        mockMvc
                .perform(get("/v1/pautas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid",
                        containsInRelativeOrder(uuid.toString(), "6d9db741-ef57-4d5a-ac0f-34f68fb0ab5e")))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder(titulo, "SEGUNDO-TITULO")))
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder( descricao,"SEGUNDA-DESCICAO")));
    }

    public ResultMatcher exists(final String expectedFieldValue) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            assertThat(json.contains(expectedFieldValue)).isTrue();
        };
    }
}