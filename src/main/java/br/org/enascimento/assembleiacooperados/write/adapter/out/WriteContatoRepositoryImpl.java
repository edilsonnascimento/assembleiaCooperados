package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ContatoExcepetion;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CONTATO_NOT_EXIST;

@Repository
public class WriteContatoRepositoryImpl implements WriteContatoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteContatoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(ContatoDTO dto) {
        try {
            var sql =
                    """
                        INSERT INTO contato(telefone, operadora, nome, codigo)
                        VALUES(:telefone, :operadora, :nome, :codigo)
                    """;
            var parameters = new MapSqlParameterSource()
                    .addValue("telefone", dto.telefone())
                    .addValue("operadora", dto.operadora().toString())
                    .addValue("nome", dto.nomeContato())
                    .addValue("codigo", dto.codigo());
            jdbcTemplate.update(sql, parameters);
        }catch (Exception e){
           throw new ContatoExcepetion(CONTATO_NOT_EXIST, e.getCause());
        }
        return true;
    }
}
