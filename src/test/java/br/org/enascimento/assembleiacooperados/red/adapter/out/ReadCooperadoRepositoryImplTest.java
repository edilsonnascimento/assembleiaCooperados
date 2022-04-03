package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ReadCooperadoRepositoryImplTest extends DataSourceHelper {

    private ReadCooperadoRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadCooperadoRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_QueryAllCooperad_MUST_RetriveSuccessful() {

        //given
        List<CooperadoInDto> expected = List.of(
                new CooperadoInDto(UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), "NOME-EXISTENTE-1", "55595585080"),
                new CooperadoInDto(UUID.fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"), "NOME-EXISTENTE-2", "77002548000"),
                new CooperadoInDto(UUID.fromString("9fd33037-09ad-4027-9ad7-6d2b83f2a5b4"), "NOME-EXISTENTE-3", "77923633096"),
                new CooperadoInDto(UUID.fromString("3136a1bc-ef61-465c-bc0c-54a65785cfb3"), "NOME-EXISTENTE-4", "37801172078"));
        //when
        List<CooperadoInDto> actual = repository.findAll();

        //then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void WHEN_QueryCooperadoByUuid_MUST_RetriveSuccessful() {

        //given
        var uuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var expected = new CooperadoInDto(uuid, "NOME-EXISTENTE-1", "55595585080");

        //when
        var actual = repository.findByUuid(uuid).get();

        //then
        assertThat(actual.getUuid()).isEqualTo(expected.uuid());
        assertThat(actual.getNome()).isEqualTo(expected.nome());
        assertThat(actual.getCpf()).isEqualTo(expected.cpf());
    }


}
