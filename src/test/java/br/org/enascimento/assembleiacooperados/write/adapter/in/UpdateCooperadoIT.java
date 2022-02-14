package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateCooperadoIT extends IntegrationHelper {

    private static final String ENDPOINT_PATH_UUID = "/v1/cooperados/{uuid}";
    private static final String ENDPOINT_PATH = "/v1/cooperados";


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
                .perform(put(ENDPOINT_PATH_UUID, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNoContent());
    }

    @Test
    void GIVEN_ValidDataOnlyNome_MUST_ReturSucess() throws Exception {
        //given
        var uuid = "1e73cdb3-0923-4452-a190-3c7eb7857e20";
        var nome = "NOVO NOME";

        var payload =
                """
                   {
                      "uuid": "%s",
                      "nome": "%s"
                   }
                """.formatted(uuid, nome);
        //when
        mockMvc
                .perform(put(ENDPOINT_PATH_UUID, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNoContent());
    }

    @Test
    void GIVEN_InValidDataPayload_MUST_ReturMessageErro() throws Exception {
        //given
        var uuid = "1e73cdb3-0923-4452-a190-3c7eb7857e20";
        var payload =
                """
                   {
                      "uuid": "%s",
                      nome": %s,
                      "cpf": %s
                   }
                """.formatted(uuid, null, null);
        //when
        mockMvc
                .perform(put(ENDPOINT_PATH_UUID, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Malformed JSON")));
    }

}
