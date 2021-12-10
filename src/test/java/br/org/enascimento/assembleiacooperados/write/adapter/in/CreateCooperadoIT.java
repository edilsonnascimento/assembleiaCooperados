package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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