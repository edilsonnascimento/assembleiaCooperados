package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WriteCedulaRepositoryImpl implements WriteCedulaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteCedulaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(CedulaInDto dto) {
        try {
            var sql =
                    """
                        INSERT INTO cedula(uuid, id_sessao, id_cooperado, voto)
                        VALUES(:uuid, :idSessao, :idCooperado, :voto)
                    """;
            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", dto.getUuid())
                    .addValue("idSessao", dto.getIdSessao())
                    .addValue("idCooperado", dto.getIdCooperado())
                    .addValue("voto", dto.getVoto().toString());

            jdbcTemplate.update(sql, parameters);
        } catch (DuplicateKeyException exception) {
            var duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            duplicatedDataException.addErrors("cooperado", "uuid ja informado");
            throw duplicatedDataException;
        }
        return true;
    }

    private Optional<Cedula> findUrnaExistente(String uuid) {
        var sqlBuscaIdCooperado = "SELECT id, uuid, id_sessao, id_cooperado FROM cedula WHERE uuid = :uuid";
        var paraCooperado = new MapSqlParameterSource()
                .addValue("uuid", uuid);
        var cooperado =
                jdbcTemplate.query(sqlBuscaIdCooperado, paraCooperado, resultSet -> {
                    var retorno = new Cedula();
                    if (resultSet.next()) {
                        retorno.setId(resultSet.getLong("id"));
                        retorno.setUuid(UUID.fromString(resultSet.getString("uuid")));
                        retorno.setIdCoopereado(resultSet.getLong("id_sessao"));
                        retorno.setIdSessao(resultSet.getLong("id_cooperado"));
                    }
                    return Optional.of(retorno);
                });
        return Optional.empty();
    }

    public Optional<EleitorDto> retrieveCedulaDto(CedulaDto dtoUrna){

        var sqlBuscaIdCooperado = "SELECT id, cpf FROM cooperado WHERE uuid = :uuid";
        var paraCooperado = new MapSqlParameterSource()
                .addValue("uuid", dtoUrna.uuidCooperado());
        var cooperado =
                jdbcTemplate.query(sqlBuscaIdCooperado, paraCooperado, resultSet -> {
                    var retorno = new Cooperado();
                    if (resultSet.next()) {
                        retorno.setId(resultSet.getLong("id"));
                        retorno.setCpf(resultSet.getString("cpf"));
                    }
                    return retorno;
                });

        var sqlBuscaIdSessao = "SELECT id FROM sessao WHERE uuid = :uuid";
        var paramSessao = new MapSqlParameterSource()
                .addValue("uuid", dtoUrna.uuidSessao());
        Long idSessao =
                jdbcTemplate.query(sqlBuscaIdSessao, paramSessao, resultSet -> {
                    if (resultSet.next()) return resultSet.getLong("id");
                    return null;
                });

        if(cooperado.getId() != null && idSessao != null) {
            var candidato =  new EleitorDto(dtoUrna.uuidCedula(),idSessao, cooperado.getId(), dtoUrna.voto());
            candidato.setCpf(cooperado.getCpf());
            return Optional.of(candidato);
        }
        return Optional.empty();
    }
}













