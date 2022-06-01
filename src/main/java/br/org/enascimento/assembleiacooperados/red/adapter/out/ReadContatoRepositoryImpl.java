package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Contato;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ReadContatoRepositoryImpl implements ReadContatoRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReadContatoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<ContatoOutDTO>> findAll() {
        var sql = """
                     SELECT nome, telefone, operadora, created_at, codigo
                     FROM contato 
                     ORDER BY created_at DESC
                     LIMIT 3
                  """;

        var dto = jdbcTemplate.query(sql, (rs, rowNum) ->
                new ContatoOutDTO(
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        Operadora.valueOf(rs.getString("operadora")),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("codigo")));
        return dto.isEmpty() ? Optional.empty() : Optional.of(dto);
    }

    @Override
    public Optional<Contato> findByCodigo(String codigo) {
        var sql = """
                     SELECT id, nome, telefone, operadora, created_at, updated_at, codigo
                     FROM contato
                     WHERE codigo = :codigo
                  """;
        var parameters = new MapSqlParameterSource()
                .addValue("codigo", codigo);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var contato = new Contato();
                contato.setId(resultSet.getLong("id"));
                contato.setNome(resultSet.getString("nome"));
                contato.setTelefone(resultSet.getString("telefone"));
                contato.setOperadora(Operadora.valueOf(resultSet.getString("operadora")));
                contato.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                contato.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                contato.setCodigo(resultSet.getString("codigo"));
                return Optional.of(contato);
            }
            return Optional.empty();
        });
    }
}