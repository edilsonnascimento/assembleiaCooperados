package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.PautaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class PautaRepositoryImpl implements PautaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public PautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void create(Pauta pauta) {
        String sql = """
            INSERT INTO pauta(uuid, titulo, descricao)
            values (:uuid, :titulo, :descricao)""";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("uuid", pauta.getUuid())
                .addValue("titulo", pauta.getTitulo())
                .addValue("descricao", pauta.getDescricao());

        jdbcTemplate.update(sql, parameters);
    }

}
