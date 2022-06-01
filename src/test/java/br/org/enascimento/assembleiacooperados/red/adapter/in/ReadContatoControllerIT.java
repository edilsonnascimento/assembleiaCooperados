package br.org.enascimento.assembleiacooperados.red.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static br.org.enascimento.assembleiacooperados.common.Consts.PATH_CONTATOS;
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
            .andExpect(jsonPath("$[*].nomeContato", containsInRelativeOrder("Miss Essie Hane", "Lynda Grady")))
            .andExpect(jsonPath("$[*].telefone", containsInRelativeOrder("45871259476", "4688825947")))
            .andExpect(jsonPath("$[*].operadora.nome", containsInRelativeOrder("VIVO", "OI")))
            .andExpect(jsonPath("$[*].operadora.codigo", containsInRelativeOrder(15, 14)))
            .andExpect(jsonPath("$[*].operadora.categoria", containsInRelativeOrder("Celular", "Celular")))
            .andExpect(jsonPath("$[*].dataCadastro", containsInRelativeOrder("2022-05-09T04:41:31.910465", "2022-05-08T04:40:53.110967")))
            .andExpect(jsonPath("$[*].codigo", containsInRelativeOrder("53432233", "53432232")));
    }

    @Test
    void DEVE_Retornar_Contato_Pelo_Codigo() throws Exception {
        var codigo = "53432233";
        mockMvc
                .perform(get(PATH_CONTATOS + "/{codigo}", codigo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists("Miss Essie Hane"))
                .andExpect(exists("45871259476"))
                .andExpect(exists("VIVO"))
                .andExpect(exists("15"))
                .andExpect(exists("Celular"))
                .andExpect(exists("2022-05-09T04:41:31.910465"))
                .andExpect(exists("53432233"));
    }
}