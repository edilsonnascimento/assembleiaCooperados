package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static br.org.enascimento.assembleiacooperados.common.Consts.PATH_CONTATOS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateContatoIT extends IntegrationHelper {

    @Test
    void DADO_Contato_VALIDO_Enviar_Criacao_RetornarCreate() throws Exception {
         //given
         var dto = new ContatoDTO(new BigDecimal("416352535"), Operadora.GVT, "Contato");

        //when
        var payload =
                """
                     {
                        "telefone": "%s",
                        "operadora": "%s",
                        "nome": "%s"
                    }
                """.formatted(dto.telefone(), dto.operadora(), dto.nome());

        //then
        mockMvc
                .perform(post(PATH_CONTATOS)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",PATH_CONTATOS));
    }
}