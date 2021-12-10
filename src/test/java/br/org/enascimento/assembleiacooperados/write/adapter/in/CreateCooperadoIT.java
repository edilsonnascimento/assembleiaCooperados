package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.ResultMatcher;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = "test.dataset=CreateCooperadoIT")
class CreateCooperadoIT extends IntegrationHelper {

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var nome = faker.name().fullName();
        var cpf = faker.number().digits(11);

        var payload =
                """
                   {
                      "uuid": "%s",
                      "nome": "%s",
                      "cpf": "%s"
                   }
                """.formatted(uuid, nome, cpf);
        //when
        mockMvc
                .perform(post("/v1/cooperados/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());
    }

}