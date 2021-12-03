package br.org.enascimento.assembleiacooperados.red.adapter.out;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class ReadPautaRepositoryImpl {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadPautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<PautaDto> findAll() {

        var sql = "SELECT uuid, titulo, descricao FROM pauta";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PautaDto(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("descricao"))
        );
    }
}
