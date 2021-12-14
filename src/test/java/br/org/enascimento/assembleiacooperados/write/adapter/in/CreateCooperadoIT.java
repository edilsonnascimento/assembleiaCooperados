package br.org.enascimento.assembleiacooperados.write.adapter.in;

import helper.IntegrationHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CreateCooperadoIT extends IntegrationHelper {

    private static final String ENDPOINT_PATH = "/v1/cooperados/";

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
                .perform(post(ENDPOINT_PATH, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated());
    }

    @Test
    void GIVEN_InValidPayload_MUST_ReturnExption() throws Exception {
        //given
        var uuid = randomUUID().toString();
        var nome = faker.name().fullName();

        var payload =
                """
                   {
                      "uuid": "%s",
                      "nome": "%s",
                      "cpf": 
                   }
                """.formatted(uuid, nome);
        //when
        mockMvc
                .perform(post(ENDPOINT_PATH, uuid)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(jsonPath("$.message", is("Malformed JSON")));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void GIVEN_InvalidData_MUST_ReturnBadRequest(String uuid,
                                                 String nome,
                                                 String cpf,
                                                 String[] errorsFields,
                                                 String[] errorsDetails) throws Exception {
        //gieven
        uuid = uuid == null ? null : String.format("\"%s\"", uuid);
        //when
        var payload =
                """
                    {
                        "uuid": %s,
                        "nome": "%s",
                        "cpf": "%s"
                    }
                """.formatted(uuid, nome, cpf);
        //then
        mockMvc
                .perform(post(ENDPOINT_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("invalid data")))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder(errorsFields)))
                .andExpect(jsonPath("$.errors[*].detail", containsInAnyOrder(errorsDetails)));

    }

    private static Stream<Arguments> provideInvalidData() {

        var uuidValido = UUID.randomUUID().toString();
        var nomeValido = faker.name().fullName();
        var cpfValido = faker.number().digits(11);

        return Stream.of(
                arguments(null, nomeValido, cpfValido, args("uuid"), args("must not be null")),
                arguments("", nomeValido, cpfValido, args("uuid"), args("must not be null")),
                arguments(uuidValido, null, cpfValido, args("nome"), args("size must be between 5 and 100")),
                arguments(uuidValido, "", cpfValido, args("nome"), args("size must be between 5 and 100")),
                arguments(uuidValido, nomeValido, null, args("cpf"), args("Size must be 11")),
                arguments(uuidValido, nomeValido, "", args("cpf"), args("Size must be 11"))
        );
    }


}