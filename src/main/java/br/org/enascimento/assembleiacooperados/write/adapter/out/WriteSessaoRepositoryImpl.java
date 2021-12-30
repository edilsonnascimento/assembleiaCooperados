package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WriteSessaoRepositoryImpl implements WriteSessaoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteSessaoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Sessao sessao) {
        try{

            var sql = """
                    INSERT INTO sessao(
                            uuid,
                            id_pauta,                            
                            id_urna,
                            inicio_sessao,
                            fim_sessao,
                            total_votos_favor,
                            total_votos_contra,
                            id_status)
                    values (                      
                            :uuid,
                            :idPauta,
                            :idUrna,
                            :inicioSessao,
                            :fimSessao,
                            :totalVotosFavor,
                            :totalVotosContra,
                            :idStatus
                      )
                  """;
            var parameters = new MapSqlParameterSource()
                    .addValue("uuid", sessao.getUuid())
                    .addValue("idPauta", sessao.getIdPauta())
                    .addValue("idUrna", sessao.getIdUrna())
                    .addValue("inicioSessao", sessao.getInicioSessao())
                    .addValue("fimSessao", sessao.getFimSessao())
                    .addValue("totalVotosFavor", sessao.getTotalVotosFavor())
                    .addValue("totalVotosContra", sessao.getTotalVotosContra())
                    .addValue("idStatus", sessao.getIdStatus());

            jdbcTemplate.update(sql, parameters);
        }catch (DuplicateKeyException exception){
            var duplicateException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            throw duplicateException;
        }
        return true;
    }
}
