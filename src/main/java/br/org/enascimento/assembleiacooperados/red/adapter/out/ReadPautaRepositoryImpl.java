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

    private static final String ID = "id";
    private static final String CAMPO_UUID = "uuid";
    private static final String TITULO = "titulo";
    private static final String DESCRICAO = "descricao";
    private static final String DATA_CRIACAO = "created_at";
    private static final String DATA_ALTERACAO = "updated_at";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadPautaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<PautaInDto> findAll() {

        var sql = "SELECT uuid, titulo, descricao, created_at FROM pauta ORDER BY created_at";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new PautaInDto(
                        UUID.fromString(rs.getString(CAMPO_UUID)),
                        rs.getString(TITULO),
                        rs.getString(DESCRICAO))
        );
    }

    @Override
    public Optional<Pauta> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, titulo, descricao, created_at, updated_at
                FROM pauta
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue(CAMPO_UUID, uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Pauta().
                        setId(resultSet.getLong(ID)).
                        setUuid(UUID.fromString(resultSet.getString(CAMPO_UUID))).
                        setTitulo(resultSet.getString(TITULO)).
                        setDescricao(resultSet.getString(DESCRICAO)).
                        setCreatedAt(resultSet.getTimestamp(DATA_CRIACAO).toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp(DATA_ALTERACAO).toLocalDateTime())
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
                .addValue(CAMPO_UUID, uuid)
                .addValue(TITULO, titulo);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Pauta().
                        setId(resultSet.getLong(ID)).
                        setUuid(UUID.fromString(resultSet.getString(CAMPO_UUID))).
                        setTitulo(resultSet.getString(TITULO)).
                        setDescricao(resultSet.getString(DESCRICAO)).
                        setCreatedAt(resultSet.getTimestamp(DATA_CRIACAO).toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp(DATA_ALTERACAO).toLocalDateTime())
                );
            }
            return Optional.empty();
        });
    }
}