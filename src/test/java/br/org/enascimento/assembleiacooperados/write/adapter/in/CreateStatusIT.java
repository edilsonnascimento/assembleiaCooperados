package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateStatusIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/status/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var descricao = faker.lorem().characters(50);

        var payload ="""
                              {
                                  "descricao": "%s"
                              }
                            """.formatted(descricao);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

    }
}
