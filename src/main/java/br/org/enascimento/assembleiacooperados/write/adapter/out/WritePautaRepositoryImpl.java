package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
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

    private static final String TITULO_FIELD = "titulo";
    private static final String DESCRICAO_FIELD = "descricao";
    private static final String UUID_FIELD = "uuid";

    private NamedParameterJdbcTemplate jdbcTemplate;
    private ReadPautaRepository repositoryRead;

    public WritePautaRepositoryImpl(DataSource dataSource, ReadPautaRepository repositoryRead) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.repositoryRead = repositoryRead;
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

            var duplicatedDataException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            var existentPauta = repositoryRead.findByUuidOrTitulo(pauta.getUuid(), pauta.getTitulo()).get();

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
    public void update(Pauta pauta) {
        String sql = """
                UPDATE pauta
                SET %s = :%s, 
                    %s = :%s
                WHERE %s = :%s
            """.formatted(TITULO_FIELD, TITULO_FIELD,
                          DESCRICAO_FIELD, DESCRICAO_FIELD,
                          UUID_FIELD, UUID_FIELD);

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TITULO_FIELD, pauta.getTitulo())
                .addValue(DESCRICAO_FIELD, pauta.getDescricao())
                .addValue(UUID_FIELD, pauta.getUuid());

        jdbcTemplate.update(sql, parameters);
    }

    public List<Pauta> findAll() {
        var sql = "SELECT * FROM pauta";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pauta.class));
    }

}
