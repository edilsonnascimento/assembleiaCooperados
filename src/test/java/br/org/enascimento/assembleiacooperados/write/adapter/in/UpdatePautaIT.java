package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdatePautaIT extends IntegrationHelper {


    @Test
    void GIVEN_InvalidDataFieldTituloBigger_MUST_ReturError() throws Exception {
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
                .perform(put("/v1/pautas/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

    }

    @Test
    void GIVEN_InvalidBodyEmpty_MUST_ReturError() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.lorem().characters();
        var descricao = faker.lorem().characters();

        var payload = "{}";
        //when
        mockMvc
                .perform(put("/v1/pautas/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());

    }

}
