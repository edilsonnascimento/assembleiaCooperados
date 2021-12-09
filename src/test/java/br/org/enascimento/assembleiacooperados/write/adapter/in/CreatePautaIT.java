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

@TestPropertySource(properties = "test.dataset=CreatePautaIT")
class CreatePautaIT extends IntegrationHelper {

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters(10);

        var payload =
                """
                   {
                      "uuid": "%s",
                      "titulo": "%s",
                      "descricao": "%s"
                   }
                """.formatted(uuid, titulo, descricao);
        //when
        mockMvc
                .perform(post("/v1/pautas/", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());

        //then
        mockMvc
                .perform(get("/v1/pautas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(exists(uuid))
                .andExpect(exists(titulo))
                .andExpect(exists(descricao));
    }

    public ResultMatcher exists(final String expectedFieldValue) {
        return mvcResult -> {
            String json = mvcResult.getResponse().getContentAsString();
            assertThat(json.contains(expectedFieldValue)).isTrue();
        };
    }
}