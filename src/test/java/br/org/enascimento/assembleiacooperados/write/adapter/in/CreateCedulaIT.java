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

public class CreateCedulaIT extends IntegrationHelper {

    private static final String URI_PATH = "/v1/cedulas/";

    @Test
    void GIVEN_ValidPayload_MUST_ReturnCreated() throws Exception {
        //given
        var uuidCedula = UUID.fromString("3b54e51d-6340-47b3-a15d-0d28e4272551");
        var dto = new CedulaDto(
                uuidCedula,
                UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3"),
                UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"),
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
