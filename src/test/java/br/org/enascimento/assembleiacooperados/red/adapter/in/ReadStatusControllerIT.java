package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReadStatusControllerIT extends IntegrationHelper {

    private final static String PATH_URI_UUID = "/v1/status/{uuid}";
    private static final String PATH_URI = "/v1/status";

    @Test
    void WHEN_GetStatusById_MUST_RetrunStatus() throws Exception {
        //given
        var id = 1l;
        mockMvc
                .perform(get(PATH_URI_UUID, id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists("ABERTA"));
    }

    @Test
    void WHEN_GetAllStatus_MUST_ListByCreated_atOrder() throws Exception {
        //given
        mockMvc
                .perform(get(PATH_URI))
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder("ABERTA", "ENCERRADA")));
    }

}
