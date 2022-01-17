package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CandidatoDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cooperado;
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
                .addValue("uuid", dto.getUuid())
                .addValue("idSessao", dto.getIdSessao())
                .addValue("idCooperado", dto.getIdCooperado())
                .addValue("voto", dto.getVoto().toString());
        
        jdbcTemplate.update(sql, parameters);
        return true;
    }

    public Optional<CandidatoDto> retrieveUrnaDto(CedulaDto dtoUrna){

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
            var candidato =  new CandidatoDto(dtoUrna.uuidUrna(),idSessao, cooperado.getId(), dtoUrna.voto());
            candidato.setCpf(cooperado.getCpf());
            return Optional.of(candidato);
        }
        return Optional.empty();
    }
}













