package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.WritePautaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WritePautaRepositoryImpl implements WritePautaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WritePautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void create(Pauta pauta) {
        try {
            String sql = """
                    INSERT INTO pauta(uuid, titulo, descricao)
                    values (:uuid, :titulo, :descricao)""";

            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", pauta.getUuid())
                    .addValue("titulo", pauta.getTitulo())
                    .addValue("descricao", pauta.getDescricao());

            jdbcTemplate.update(sql, parameters);
        } catch (DuplicateKeyException exception) {

            DuplicatedDataException duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            var existentPauta = findByUuidOrTitulo(pauta.getUuid(), pauta.getTitulo()).get();

            if(existentPauta.getTitulo().equals(pauta.getTitulo())) {
                duplicatedDataException.addErrors("titulo", pauta.getTitulo());
            }

            if(existentPauta.getUuid().equals(pauta.getUuid())) {
                duplicatedDataException.addErrors("uuid", pauta.getUuid());
            }

            throw duplicatedDataException;
        }
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
                        setId(resultSet.getInt("id")).
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
                        setId(resultSet.getInt("id")).
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
    public void update(Pauta pauta) {
        String sql = """
            UPDATE pauta
            SET titulo = :titulo, descricao = :descricao
            WHERE uuid = :uuid""";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("uuid", pauta.getUuid())
                .addValue("titulo", pauta.getTitulo())
                .addValue("descricao", pauta.getDescricao());

        jdbcTemplate.update(sql, parameters);
    }

    public List<Pauta> findAll() {
        var sql = "SELECT * FROM pauta";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pauta.class));
    }

}
