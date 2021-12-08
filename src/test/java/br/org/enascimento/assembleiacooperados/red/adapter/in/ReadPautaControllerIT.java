package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource(properties = "test.dataset=ReadPautaControllerIT")
class ReadPautaControllerIT extends IntegrationHelper {

    @Test
    void WHEN_GetAllPautas_MUST_ListByCreated_atOrder() throws Exception {
        mockMvc
                .perform(get("/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder("1e73cdb3-0923-4452-a190-3c7eb7857e20", "3731c747-ea27-42e5-a52b-1dfbfa9617db")))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder("PRIMEIRO-TITULO", "SEGUNDO-TITULO")))
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder("PRIMEIRA-DESCICAO", "SEGUNDA-DESCICAO")));
    }
}
