package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CreateSessaoIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/sessao/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = UUID.randomUUID();
        var uuidPauta = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var limiteSessao = 5L;
        var payload =
                """
                   {
                       "%s" : "%s",
                       "%s" : "%s",
                       "%s" : %s
                   }
                """.formatted("uuid", uuid,"uuidPauta", uuidPauta, "limiteSessao", limiteSessao);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "v1/sessao/" + uuid));
    }

    @Test
    void GIVEN_ValidEmptyLimiteSessaoPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = UUID.randomUUID();
        var uuidPauta = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        Long limiteSessao = null;
        var payload =
                """
                   {
                       "%s" : "%s",
                       "%s" : "%s",
                       "%s" : %s
                   }
                """.formatted("uuid", uuid,"uuidPauta", uuidPauta, "limiteSessao", limiteSessao);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "v1/sessao/" + uuid));
    }

    @Test
    void GIVEN_InValidPayloadDuplicate_MUST_ReturnBadRequest() throws Exception {
        //given
        var uuid = UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3");
        var uuidPauta = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var limiteSessao = 5L;
        var payload =
                """
                   {
                       "%s" : "%s",
                       "%s" : "%s",
                       "%s" : %s
                   }
                """.formatted("uuid", uuid,"uuidPauta", uuidPauta, "limiteSessao", limiteSessao);
        //when
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid duplicated data")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("code")))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder("1000")));
    }
}
