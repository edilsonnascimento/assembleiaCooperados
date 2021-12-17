package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReadStatusControllerIT extends IntegrationHelper {

    private final static String PATH_URI_UUID = "/v1/status/{uuid}";

    @Test
    void WHEN_GetStatusById_MUST_RetrunStatus() throws Exception {
        //given
        var id = 1l;
        mockMvc
                .perform(get(PATH_URI_UUID, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists("Ativo"));
    }

}
