package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReadCedulaControllerIT extends IntegrationHelper {

    private final static String PATH_URI_UUID = "/v1/cedulas/{uuid}";
    private final static String PATH_URI = "/v1/cedulas";

    @Test
    void WHEN_GetCedualaByUuid_MUST_RetrunCedula() throws Exception {
        //given
        var uuid = "0d28786f-8dbd-41f7-8a77-59ea8bed7d8c";
        //when
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.uuidCedula", is("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c")))
                .andExpect(jsonPath("$.uuidSessao", is("91459bb4-07e9-47ab-85c5-4af513db36a3")))
                .andExpect(jsonPath("$.uuidCooperado", is("1e73cdb3-0923-4452-a190-3c7eb7857e20")))
                .andExpect(jsonPath("$.dataVoto", is("2021-12-08T05:55:00.901884")))
                .andExpect(jsonPath("$.voto", is("FAVORAVEL")));
    }

    @Test
    void WHEN_GetInvalidPautasByUuid_MUST_RetrunMesage() throws Exception {
        //given
        var uuid = UUID.randomUUID().toString();
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Cedula not exist")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("code")))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder("1010")));
    }

    @Test
    void WHEN_GetAllCedulas_MUST_ListByCreated_atOrder() throws Exception {
        mockMvc
                .perform(get(PATH_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuidCedula", containsInRelativeOrder("0d28786f-8dbd-41f7-8a77-59ea8bed7d8c", "147e966b-7b4a-4702-b322-ba0d5b707d18")))
                .andExpect(jsonPath("$[*].uuidSessao", containsInRelativeOrder("91459bb4-07e9-47ab-85c5-4af513db36a3", "91459bb4-07e9-47ab-85c5-4af513db36a3")))
                .andExpect(jsonPath("$[*].uuidCooperado", containsInRelativeOrder("1e73cdb3-0923-4452-a190-3c7eb7857e20", "3731c747-ea27-42e5-a52b-1dfbfa9617db")))
                .andExpect(jsonPath("$[*].dataVoto", containsInRelativeOrder("2021-12-08T05:55:00.901884", "2021-12-08T05:56:00.901884")))
                .andExpect(jsonPath("$[*].voto", containsInRelativeOrder("FAVORAVEL", "CONTRA")));
    }

}
