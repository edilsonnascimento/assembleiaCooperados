package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.EleitorDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteCedulaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CEDULA_NOT_EXIST;
import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WriteCedulaRepositoryImpl implements WriteCedulaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WriteCedulaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(CedulaInDto cedulaInDto) {
        try {
            var sql =
                    """
                        INSERT INTO cedula(uuid, id_sessao, id_cooperado, voto)
                        VALUES(:uuid, :idSessao, :idCooperado, :voto)
                    """;
            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", cedulaInDto.getUuid())
                    .addValue("idSessao", cedulaInDto.getIdSessao())
                    .addValue("idCooperado", cedulaInDto.getIdCooperado())
                    .addValue("voto", cedulaInDto.getVoto().toString());

            jdbcTemplate.update(sql, parameters);
        } catch (DuplicateKeyException exception) {
            var duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            var optionalCedula = findCedula(cedulaInDto);
            if(!optionalCedula.isPresent())
                throw new CedulaNotExistentException(CEDULA_NOT_EXIST);
            var cedulaDuplicated = optionalCedula.get();

            if(cedulaDuplicated.getUuid().equals(cedulaInDto.getUuid()))
                duplicatedDataException.addErrors("uuid", cedulaInDto.getUuid());

            if(cedulaDuplicated.getIdCoopereado().equals(cedulaInDto.getIdCooperado()) &&
               cedulaDuplicated.getIdSessao().equals(cedulaInDto.getIdSessao()))
                duplicatedDataException.addErrors("cooperado", cedulaInDto.getUuid());

            throw duplicatedDataException;
        }
        return true;
    }

    private Optional<Cedula> findCedula(CedulaInDto cedulaInDto) {
        var sql = """
            SELECT id, uuid, id_sessao, id_cooperado, voto 
            FROM cedula 
            WHERE uuid = :uuid OR 
                  (id_sessao = :idSessao AND id_cooperado = :idCooperado)""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", cedulaInDto.getUuid())
                .addValue("idSessao", cedulaInDto.getIdSessao())
                .addValue("idCooperado", cedulaInDto.getIdCooperado());
        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var cedula = new Cedula();
                cedula.setId(resultSet.getLong("id"));
                cedula.setUuid(UUID.fromString(resultSet.getString("uuid")));
                cedula.setIdSessao(resultSet.getLong("id_sessao"));
                cedula.setIdCoopereado(resultSet.getLong("id_cooperado"));
                cedula.setVoto(Voto.valueOf(resultSet.getString("voto")));
                return Optional.of(cedula);
            }
            return Optional.empty();
        });
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

        if(cooperado != null && idSessao != null) {
            var eleitor =  new EleitorDto(dtoUrna.uuidCedula(),idSessao, cooperado.getId(), dtoUrna.voto());
            eleitor.setCpf(cooperado.getCpf());
            return Optional.of(eleitor);
        }
        return Optional.empty();
    }
}