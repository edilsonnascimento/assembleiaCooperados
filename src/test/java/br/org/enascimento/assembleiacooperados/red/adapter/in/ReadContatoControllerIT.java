package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static br.org.enascimento.assembleiacooperados.common.PathURI.PATH_CONTATOS;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class ReadContatoControllerIT extends IntegrationHelper {

    @Test
    void DEVE_Listar_Todos_Contatos() throws Exception {
        mockMvc
                .perform(get(PATH_CONTATOS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].nomeContato", containsInRelativeOrder("Brandi Morissette", "Lynda Grady")))
                .andExpect(jsonPath("$[*].telefone", containsInRelativeOrder("4199487497", "45871259476")))
                .andExpect(jsonPath("$[*].nomeOperadora", containsInRelativeOrder("TIM", "OI")))
                .andExpect(jsonPath("$[*].dataCadastro", containsInRelativeOrder("2022-05-08T04:38:54.577823", "2022-05-08T04:40:53.110967")));
    }
}
