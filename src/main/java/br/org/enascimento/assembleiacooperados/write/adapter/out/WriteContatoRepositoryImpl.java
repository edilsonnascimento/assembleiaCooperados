package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;

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
                        INSERT INTO contato(telefone, operadora, nome)
                        VALUES(:telefone, :operadora, :nome)
                    """;
            var parameters = new MapSqlParameterSource()
                    .addValue("telefone", dto.telefone())
                    .addValue("operadora", dto.operadora().toString())
                    .addValue("nome", dto.nome());
            jdbcTemplate.update(sql, parameters);
        }catch (Exception e){
           throw new DuplicatedDataException(PAUTA_NOT_EXIST);
        }
        return true;
    }
}
