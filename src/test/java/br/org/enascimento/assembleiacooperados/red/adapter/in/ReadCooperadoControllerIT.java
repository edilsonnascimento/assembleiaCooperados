
package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ReadCooperadoControllerIT extends IntegrationHelper {

    @Test
    void WHEN_GetAllCooperados_MUST_ListByCreated_atOrder() throws Exception {
        mockMvc
                .perform(get("/v1/cooperados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder("1e73cdb3-0923-4452-a190-3c7eb7857e20", "3731c747-ea27-42e5-a52b-1dfbfa9617db")))
                .andExpect(jsonPath("$[*].nome", containsInRelativeOrder("NOME-EXISTENTE-1", "NOME-EXISTENTE-2")))
                .andExpect(jsonPath("$[*].cpf", containsInRelativeOrder("55595585080", "77002548000")));
    }

    @Test
    void WHEN_GetCooperado_FindByUuid_MUST_ReturnCooperado() throws Exception {
        var uuid = "1e73cdb3-0923-4452-a190-3c7eb7857e20";
        mockMvc
                .perform(get("/v1/cooperados/{uuid}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists(uuid))
                .andExpect(exists("NOME-EXISTENTE-1"))
                .andExpect(exists("55595585080"));
    }

    @Test
    void WHEN_GetCooperado_FindByUuid_MUST_ReturnMensageError() throws Exception {
        var uuid = UUID.randomUUID().toString();
        mockMvc
                .perform(get("/v1/cooperados/{uuid}", uuid))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Cooperado not exist")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("code")))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder("1004")));
    }
}
