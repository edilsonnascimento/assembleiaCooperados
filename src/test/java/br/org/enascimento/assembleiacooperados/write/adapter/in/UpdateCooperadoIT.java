package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UpdateCooperadoIT extends IntegrationHelper {

    private static final String ENDPOINT_PATH = "/v1/cooperados/{uuid}";

    @Test
    void GIVEN_ValidData_MUST_ReturSucess() throws Exception {
        //given
        var uuid = "1e73cdb3-0923-4452-a190-3c7eb7857e20";
        var nome = "NOVO TITULO";
        var cpf = "00000000000";

        var payload =
                """
                   {
                      "uuid": "%s",
                      "nome": "%s",
                      "cpf": "%s"
                   }
                """.formatted(uuid, nome, cpf);
        //when
        mockMvc
                .perform(put(ENDPOINT_PATH, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNoContent());

        //then
        mockMvc
                .perform(get("/v1/cooperados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder("1e73cdb3-0923-4452-a190-3c7eb7857e20", "3731c747-ea27-42e5-a52b-1dfbfa9617db")))
                .andExpect(jsonPath("$[*].nome", containsInRelativeOrder("NOVO TITULO", "NOME-EXISTENTE-2")))
                .andExpect(jsonPath("$[*].cpf", containsInRelativeOrder("00000000000", "38176004707")));
    }

}
