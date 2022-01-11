package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaIntoDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteUrnaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class WriteUrnaRepositoryImpl implements WriteUrnaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteUrnaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(UrnaInDto dto) {
        var sql =
                """
                    INSERT INTO urna(uuid, id_sessao, id_cooperado, voto)
                    VALUES(:uuid, :idSessao, :idCooperado, :voto)
                """;
        var parameters = new MapSqlParameterSource()
                .addValue("uuid", dto.uuid())
                .addValue("idSessao", dto.idSessao())
                .addValue("idCooperado", dto.idCooperado())
                .addValue("voto", dto.voto().toString());
        
        jdbcTemplate.update(sql, parameters);
        return true;
    }

    public Optional<UrnaInDto> retrieveUrnaDto(UrnaIntoDto dtoUrna){

        var sqlBuscaIdCooperado = "SELECT id FROM cooperado WHERE uuid = :uuid";
        var paraCooperado = new MapSqlParameterSource()
                .addValue("uuid", dtoUrna.uuidCooperado());
        Long idCooperado =
                jdbcTemplate.query(sqlBuscaIdCooperado, paraCooperado, resultSet -> {
                    if (resultSet.next()) return resultSet.getLong("id");
                    return null;
                });

        var sqlBuscaIdSessao = "SELECT id FROM sessao WHERE uuid = :uuid";
        var paramSessao = new MapSqlParameterSource()
                .addValue("uuid", dtoUrna.uuidSessao());
        Long idSessao =
                jdbcTemplate.query(sqlBuscaIdSessao, paramSessao, resultSet -> {
                    if (resultSet.next()) return resultSet.getLong("id");
                    return null;
                });

        if(idCooperado != null && idSessao != null)
            return Optional.of(new UrnaInDto(dtoUrna.uuidUrna(),idSessao, idCooperado, dtoUrna.voto()));

        return Optional.empty();
    }
}













