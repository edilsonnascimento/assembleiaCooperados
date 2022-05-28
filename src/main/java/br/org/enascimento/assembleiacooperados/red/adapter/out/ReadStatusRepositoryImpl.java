package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ReadStatusRepositoryImpl implements ReadStatusRepository {

    private static final String ID = "id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReadStatusRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Status> findById(Long id) {
        var sql = """
                SELECT id, descricao, created_at, updated_at
                FROM status
                WHERE id = :id""";

        var parameters = new MapSqlParameterSource()
                .addValue(ID, id);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var status = new Status();
                status.setId(resultSet.getLong(ID));
                status.setDescricao(resultSet.getString("descricao"));
                status.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                status.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                return Optional.of(status);
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<List<Status>> findAll() {
        var sql = "SELECT id, descricao, created_at, updated_at FROM status ORDER BY created_at";

        return Optional.of(jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Status(resultSet.getLong(ID),
                           resultSet.getString("descricao"),
                           resultSet.getTimestamp("created_at").toLocalDateTime(),
                           resultSet.getTimestamp("updated_at").toLocalDateTime())
        ));
    }
}
