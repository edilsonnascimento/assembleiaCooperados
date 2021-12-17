package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteStatusRepositoy;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class WriteStatusRepositoryImpl implements WriteStatusRepositoy {

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
}
