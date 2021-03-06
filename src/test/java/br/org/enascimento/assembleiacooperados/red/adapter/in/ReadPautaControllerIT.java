package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ReadPautaControllerIT extends IntegrationHelper {

    private final static String PATH_URI = "/v1/pautas";
    private final static String PATH_URI_UUID = "/v1/pautas/{uuid}";

    @Test
    void WHEN_GetAllPautas_MUST_ListByCreated_atOrder() throws Exception {
        mockMvc
                .perform(get(PATH_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder("1e73cdb3-0923-4452-a190-3c7eb7857e20", "3731c747-ea27-42e5-a52b-1dfbfa9617db")))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder("PRIMEIRO-TITULO", "SEGUNDO-TITULO")))
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder("PRIMEIRA-DESCICAO", "SEGUNDA-DESCICAO")));
    }

    @Test
    void WHEN_GetPautasByUuid_MUST_RetrunPauta() throws Exception {
        //given
        var uuid = "1e73cdb3-0923-4452-a190-3c7eb7857e20";
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists("1e73cdb3-0923-4452-a190-3c7eb7857e20"))
                .andExpect(exists("PRIMEIRO-TITULO"))
                .andExpect(exists("PRIMEIRA-DESCICAO"));
    }

    @Test
    void WHEN_GetInvalidPautasByUuid_MUST_RetrunMesage() throws Exception {
        //given
        var uuid = UUID.randomUUID().toString();
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Pauta not exist")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("code")))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder("1001")));
    }
}
