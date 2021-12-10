package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.CooperadoDto;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class ReadCooperadoRepositoryImpl implements ReadCooperadoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadCooperadoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<CooperadoDto> findAll() {
        var sql = "SELECT uuid, nome, cpf, created_at FROM cooperado ORDER BY created_at";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CooperadoDto(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("nome"),
                        rs.getString("cpf"))
        );
    }
}

