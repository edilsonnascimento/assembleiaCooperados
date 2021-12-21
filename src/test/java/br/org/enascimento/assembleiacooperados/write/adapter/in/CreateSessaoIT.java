package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateSessaoIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/sessao/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = UUID.randomUUID();
        var uuidPauta = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var payload =
                """
                   {
                       "%s" : "%s",
                       "%s" : "%s"
                   }
                """.formatted("uuid", uuid,"uuidPauta", uuidPauta);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "v1/sessao/" + uuid));


    }
}
