package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.domain.application.UpdatePautaCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(properties = "test.dataset=WritePautaControllerIT")
class WritePautaControllerIT extends IntegrationHelper {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void GIVEN_ValidData_MUST_UpdatePauta() throws Exception {
        //given
        var titulo = faker.team().sport();
        var descricao = faker.lorem().characters();
        var uuid = UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db");
        var command = new UpdatePautaCommand(uuid, titulo, descricao);

        //when
        mockMvc
                .perform(put("/v1/pautas/{uuid}", uuid)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(command)))
                .andExpect(status().isOk());

        //then
        mockMvc
                .perform(get("/v1/pautas/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[*].uuid", containsInRelativeOrder(uuid)))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder(titulo)))
                .andExpect(jsonPath("$[*].descricao", containsInRelativeOrder(descricao)));
    }
}