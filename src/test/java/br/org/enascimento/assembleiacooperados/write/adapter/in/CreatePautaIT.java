package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CreatePautaIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/pautas/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters(10);

        var payload ="""
                              {
                                  "uuid": "%s",
                                  "titulo": "%s",
                                  "descricao": "%s"
                              }
                            """.formatted(uuid, titulo, descricao);
        //when
        mockMvc
                .perform(post(URI_PATH, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "v1/pautas/" + uuid));

        //then
        mockMvc
                .perform(get(URI_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists(uuid))
                .andExpect(exists(titulo))
                .andExpect(exists(descricao));
    }

    @Test
    void GIVEN_DuplicatedKey_MUST_ReturnBadRequest() throws Exception {
        //given
        var duplicatedExternalUuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters(10);

        var payload =
                """
                               {
                                  "uuid": "%s",
                                  "titulo": "%s",
                                  "descricao": "%s"
                               }
                        """.formatted(duplicatedExternalUuid, titulo, descricao);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid duplicated data")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("code")))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder("1000")));
    }

}