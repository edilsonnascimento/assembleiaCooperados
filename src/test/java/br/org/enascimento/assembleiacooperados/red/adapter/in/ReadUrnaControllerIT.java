package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReadUrnaControllerIT extends IntegrationHelper {

    private final static String PATH_URI_UUID = "/v1/urnas/{uuid}";

    @Test
    void WHEN_GetUrnaByUuid_MUST_RetrunUrna() throws Exception {
        //given
        var uuid = "0d28786f-8dbd-41f7-8a77-59ea8bed7d8c";
        //when
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.uuidUrna", is("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c")))
                .andExpect(jsonPath("$.uuidSessao", is("91459bb4-07e9-47ab-85c5-4af513db36a3")))
                .andExpect(jsonPath("$.uuidCooperado", is("1e73cdb3-0923-4452-a190-3c7eb7857e20")))
                .andExpect(jsonPath("$.dataVoto", is("2021-12-08T05:55:00.901884")))
                .andExpect(jsonPath("$.voto", is("FAVORAVEL")));
    }
}
