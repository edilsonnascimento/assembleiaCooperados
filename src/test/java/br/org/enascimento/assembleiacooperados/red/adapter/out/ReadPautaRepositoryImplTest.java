package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import helper.DataSourceHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;


class ReadPautaRepositoryImplTest extends DataSourceHelper {

    private ReadPautaRepository repository;

    @BeforeEach
    void setup() {
        repository = new ReadPautaRepositoryImpl(dataSource);
    }

    @Test
    void MUST_ImplementInterface() {
        assertThat(repository).isInstanceOf(ReadPautaRepository.class);
    }

    @Test
    void WHEN_QueryAllPauta_MUST_RetriveSuccessful(){
        //given
        var expected = List.of(
                                    new PautaInDto(fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"), "PRIMEIRO-TITULO", "PRIMEIRA-DESCICAO"),
                                    new PautaInDto(fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"), "SEGUNDO-TITULO", "SEGUNDA-DESCICAO"));

        //when
        var actual = repository.findAll();

        //then
        assertThat(actual).extracting(PautaInDto::uuid)
                .contains(fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20"),
                                 fromString("3731c747-ea27-42e5-a52b-1dfbfa9617db"));
    }

    @Test
    void WHEN_QueryPautaByUuid_MUST_RetriveSuccessful(){
        //given
        var uuid = UUID.fromString("1e73cdb3-0923-4452-a190-3c7eb7857e20");
        var expected = new Pauta();
                expected.setId(1l);
                expected.setUuid(uuid);
                expected.setTitulo("PRIMEIRO-TITULO");
                expected.setDescricao("PRIMEIRA-DESCICAO");
        //when
        var actual = repository.findByUuid(uuid).get();

        //then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUuid()).isEqualTo(expected.getUuid());
        assertThat(actual.getTitulo()).isEqualTo(expected.getTitulo());
        assertThat(actual.getDescricao()).isEqualTo(expected.getDescricao());
    }
 }
