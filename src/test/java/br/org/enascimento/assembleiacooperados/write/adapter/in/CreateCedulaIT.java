package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateCedulaIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/cedulas/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuidCedula = UUID.randomUUID();
        var dto = new CedulaDto(
                uuidCedula,
                UUID.fromString("c97defa8-9451-4aa0-aaa1-a2eb1a90e6a1"),
                UUID.fromString("3136a1bc-ef61-465c-bc0c-54a65785cfb3"),
                Voto.FAVORAVEL);

        //when
        var payload =
                """
                    {
                        "uuidCedula": "%s",
                        "uuidSessao": "%s",
                        "uuidCooperado": "%s",
                        "voto": "%s"
                    }
                """.formatted(uuidCedula, dto.uuidSessao(), dto.uuidCooperado(), dto.voto().name());

        //then
        mockMvc
                .perform(post(URI_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",URI_PATH + uuidCedula));
    }
}