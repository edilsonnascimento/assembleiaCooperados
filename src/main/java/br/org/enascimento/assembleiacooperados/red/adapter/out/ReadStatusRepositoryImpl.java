package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Pauta;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadStatusRepositoryImpl implements ReadStatusRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

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
                .addValue("id", id);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new Status().
                        setId(resultSet.getLong("id")).
                        setDescricao(resultSet.getString("descricao")).
                        setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime()).
                        setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                );
            }
            return Optional.empty();
        });
    }
}
