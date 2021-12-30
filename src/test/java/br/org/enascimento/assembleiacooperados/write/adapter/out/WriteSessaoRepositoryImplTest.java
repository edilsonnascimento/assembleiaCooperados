package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import helper.DataSourceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.math.BigInteger.TEN;
import static java.math.BigInteger.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class WriteSessaoRepositoryImplTest extends DataSourceHelper {

    private WriteSessaoRepository repositoryWrite;

    @BeforeEach
    void setup() {
        repositoryWrite = new WriteSessaoRepositoryImpl(dataSource);
    }

    @Test
    void WHEN_CreatingStatus_GIVEN_ValidData_MUST_PersistOnDatabase() {
        //given
        var uuid = UUID.randomUUID();
        var idPauta = 2l;
        Long idUrna = null;
        var idStatus = 1l;
        var inicioSessao = LocalDateTime.now();
        var fimSessao = LocalDateTime.now().minusMinutes(5l);
        var totalVotosFavor = new BigDecimal(TEN);
        var totalVotosContra = new BigDecimal(TWO);
        var sessao = new Sessao()
                .setUuid(uuid)
                .setIdPauta(idPauta)
                .setIdUrna(idUrna)
                .setIdStatus(idStatus)
                .setInicioSessao(inicioSessao)
                .setFimSessao(fimSessao)
                .setTotalVotosContra(totalVotosContra)
                .setTotalVotosFavor(totalVotosFavor);
        //when
        var expected = repositoryWrite.create(sessao);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void Given_InvalidEntityDuplicate_Must_ThrowException() {
        //given
        var sessao = new Sessao();
        sessao
                .setUuid(UUID.fromString("91459bb4-07e9-47ab-85c5-4af513db36a3"))
                .setIdPauta(faker.number().randomNumber())
                .setIdUrna(faker.number().randomNumber())
                .setInicioSessao(LocalDateTime.now())
                .setFimSessao(LocalDateTime.now())
                .setTotalVotosFavor(BigDecimal.ZERO)
                .setTotalVotosContra(BigDecimal.ZERO)
                .setIdStatus(faker.number().randomNumber());

        //when
        var exception = Assertions.assertThrows(DuplicatedDataException.class, () ->
                repositoryWrite.create(sessao));

        //then
        assertThat(exception.getMessage()).isEqualTo("Invalid duplicated data");
    }

}
