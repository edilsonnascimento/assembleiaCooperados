package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class WriteStatusRepositoryImpl implements WriteStatusRepositoy {

    private static final String DESCRICAO_FIELD = "descricao";
    private static final String UPDATED_AT_FIELD = "updated_at";
    private static final String ID_FIELD = "id";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteStatusRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Status status) {
        var sql = "INSERT INTO status(descricao) VALUES(:descricao)";
        var parameters = new MapSqlParameterSource()
                .addValue("descricao", status.getDescricao());
        jdbcTemplate.update(sql, parameters);
        return true;
    }

    @Override
    public void update(Status status) {
        var sql = """
                            UPDATE status
                            SET %s = :%s,
                                %s = :%s
                            WHERE %s = :%s
                        """.formatted(DESCRICAO_FIELD, DESCRICAO_FIELD,
                                      UPDATED_AT_FIELD, UPDATED_AT_FIELD,
                                      ID_FIELD, ID_FIELD);

        var parameters = new MapSqlParameterSource()
                .addValue(DESCRICAO_FIELD, status.getDescricao())
                .addValue(UPDATED_AT_FIELD, LocalDateTime.now())
                .addValue(ID_FIELD, status.getId());

        jdbcTemplate.update(sql, parameters);
    }
}