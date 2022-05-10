package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
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
        var sql = "SELECT nome, telefone, operadora, created_at FROM contato";
        var dto = jdbcTemplate.query(sql, (rs, rowNum) ->
                new ContatoOutDTO(
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        Operadora.valueOf(rs.getString("operadora")),
                        rs.getTimestamp("created_at").toLocalDateTime()));
        return dto.isEmpty() ? Optional.empty() : Optional.of(dto);
    }
}