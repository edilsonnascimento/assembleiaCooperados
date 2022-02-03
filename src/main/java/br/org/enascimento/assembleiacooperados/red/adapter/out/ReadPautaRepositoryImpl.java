package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadPautaRepositoryImpl implements ReadPautaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadPautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<PautaInDto> findAll() {

        var sql = "SELECT uuid, titulo, descricao, created_at FROM pauta ORDER BY created_at";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PautaInDto(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("titulo"),
                        rs.getString("descricao"))
        );
    }

    @Override
    public Optional<Pauta> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, titulo, descricao, created_at, updated_at
                FROM pauta
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Pauta().
                        setId(resultSet.getLong("id")).
                        setUuid(UUID.fromString(resultSet.getString("uuid"))).
                        setTitulo(resultSet.getString("titulo")).
                        setDescricao(resultSet.getString("descricao")).
                        setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                );
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<Pauta> findByUuidOrTitulo(UUID uuid, String titulo) {
        var sql = """
                SELECT id, uuid, titulo, descricao, created_at, updated_at
                FROM pauta
                WHERE uuid = :uuid OR titulo = :titulo """;

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid)
                .addValue("titulo", titulo);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Pauta().
                        setId(resultSet.getLong("id")).
                        setUuid(UUID.fromString(resultSet.getString("uuid"))).
                        setTitulo(resultSet.getString("titulo")).
                        setDescricao(resultSet.getString("descricao")).
                        setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                );
            }
            return Optional.empty();
        });
    }
}