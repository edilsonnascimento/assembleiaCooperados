package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.PautaRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
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

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", pauta.getUuid())
                .addValue("titulo", pauta.getTitulo())
                .addValue("descricao", pauta.getDescricao());

        jdbcTemplate.update(sql, parameters);
    }

    @Override
    public Optional<Pauta> findByUuid(UUID uuid) {
        String sql = """
            SELECT id, uuid, titulo, descricao
            FROM pauta
            WHERE uuid = :uuid""";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Pauta().
                        setId(resultSet.getInt("id")).
                        setUuid(UUID.fromString(resultSet.getString("uuid"))).
                        setTitulo(resultSet.getString("titulo")).
                        setDescricao(resultSet.getString("descricao"))
                );
            }
            return Optional.empty();
        });
    }

    public List<Pauta> findAll() {
        var sql = "SELECT * FROM pauta";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pauta.class));
    }

}
