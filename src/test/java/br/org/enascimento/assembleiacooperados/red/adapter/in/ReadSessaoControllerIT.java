package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReadSessaoControllerIT extends IntegrationHelper {

    private final static String PATH_URI_UUID = "/v1/sessoes/{uuid}";
    private static final String PATH_URI = "/v1/sessoes";

    @Test
    void WHEN_GetSessaoByUuid_MUST_RetrunSessao() throws Exception {
        //given
        var uuid = "91459bb4-07e9-47ab-85c5-4af513db36a3";

        //when
        mockMvc
                .perform(get(PATH_URI_UUID, uuid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", is("91459bb4-07e9-47ab-85c5-4af513db36a3")))
                .andExpect(jsonPath("$.quantidadeParticipantes", not(0)))
                .andExpect(jsonPath("$.tituloPauta", is("PRIMEIRO-TITULO")))
                .andExpect(jsonPath("$.descricaoPauta", is("PRIMEIRA-DESCICAO")))
                .andExpect(jsonPath("$.dataInicioSessao", is("2021-12-21T08:54:06.058491")))
                .andExpect(jsonPath("$.dataFimSessao", is("2021-12-21T08:59:06.058491")))
                .andExpect(jsonPath("$.quantiadeVotosFavoraveis", is(0)))
                .andExpect(jsonPath("$.quantiadeVotosContra", is(0)))
                .andExpect(jsonPath("$.estadoSessao", is("ABERTA")));
    }

    @Test
    void WHEN_GetSessoes_MUST_RetrunList() throws Exception {

        //when
        mockMvc
                .perform(get(PATH_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder("91459bb4-07e9-47ab-85c5-4af513db36a3", "c97defa8-9451-4aa0-aaa1-a2eb1a90e6a1")))
                .andExpect(jsonPath("$[*].quantidadeParticipantes", not(0)))
                .andExpect(jsonPath("$[*].tituloPauta", containsInRelativeOrder("PRIMEIRO-TITULO", "SEGUNDO-TITULO")))
                .andExpect(jsonPath("$[*].descricaoPauta", containsInRelativeOrder("PRIMEIRA-DESCICAO", "SEGUNDA-DESCICAO")))
                .andExpect(jsonPath("$[*].dataInicioSessao", containsInRelativeOrder("2021-12-21T08:54:06.058491", "2021-12-21T08:57:06.058491")))
                .andExpect(jsonPath("$[*].dataFimSessao", containsInRelativeOrder("2021-12-21T08:59:06.058491", "2021-12-21T09:00:06.058491")))
                .andExpect(jsonPath("$[*].quantiadeVotosFavoraveis", containsInRelativeOrder(0, 0)))
                .andExpect(jsonPath("$[*].quantiadeVotosContra", containsInRelativeOrder(0,0)))
                .andExpect(jsonPath("$[*].estadoSessao", containsInRelativeOrder("ABERTA", "ABERTA")));
    }
}
