package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static br.org.enascimento.assembleiacooperados.common.Consts.PATH_OPERADORAS;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReadOperadoraControllerIT extends IntegrationHelper {

    @Test
    void DEVE_Listar_Todas_Operadoras() throws Exception {
        mockMvc
                .perform(get(PATH_OPERADORAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].codigo", containsInRelativeOrder(14, 15)))
                .andExpect(jsonPath("$[*].categoria", containsInRelativeOrder("Celular", "Celular")))
                .andExpect(jsonPath("$[*].preco", containsInRelativeOrder(2, 1)))
                .andExpect(jsonPath("$[*].nome", containsInRelativeOrder("OI", "VIVO")));
    }
}