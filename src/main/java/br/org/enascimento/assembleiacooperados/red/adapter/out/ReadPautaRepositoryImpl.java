package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.PautaDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class ReadPautaRepositoryImpl implements ReadPautaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadPautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<PautaDto> findAll() {

        var sql = "SELECT uuid, titulo, descricao, created_at FROM pauta ORDER BY created_at";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PautaDto(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("descricao"))
        );
    }
}
