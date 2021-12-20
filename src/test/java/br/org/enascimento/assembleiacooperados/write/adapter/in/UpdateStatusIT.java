package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateStatusIT extends IntegrationHelper {

    private final static String PATH_URI_ID = "/v1/status/{id}";

    @Test
    void GIVEN_ValidDataUpdateStatus_MUST_ReturnSucess() throws Exception {
        //given
        var id = 1l;
        var valor = "NOVO STATUS";
        var payload = """
                        {
                            "%s": "%s"
                        }
                     """.formatted("descricao", valor);

        //when
        mockMvc
                .perform(put(PATH_URI_ID, id)
                        .content(payload)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void GIVEN_InvalidBodyNull_MUST_ReturMesageError() throws Exception {
        var id = 1l;
        String valor = null;
        var payload = """
                        {
                            "%s": %s
                        }
                     """.formatted("descricao", valor);

        //when
        mockMvc
                .perform(put(PATH_URI_ID, id)
                        .content(payload)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void GIVEN_InvalidStatus_MUST_ReturMesageError() throws Exception {
        var id = 100000l;
        var valor = "NOVO STATUS";
        var payload = """
                        {
                            "%s": "%s"
                        }
                     """.formatted("descricao", valor);

        //when
        mockMvc
                .perform(put(PATH_URI_ID, id)
                        .content(payload)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
