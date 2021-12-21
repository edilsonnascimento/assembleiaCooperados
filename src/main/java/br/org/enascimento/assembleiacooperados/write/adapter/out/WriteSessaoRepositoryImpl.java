package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class WriteSessaoRepositoryImpl implements WriteSessaoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteSessaoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Sessao sessao) {
        var sql = """
                    INSERT INTO sessao(
                            uuid,
                            id_pauta,                            
                            id_quorum,
                            inicio_sessao,
                            fim_sessao,
                            total_votos_favor,
                            total_votos_contra,
                            id_status)
                    values (                      
                            :uuid,
                            :idPauta,
                            :idQuorum,
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
                .addValue("idQuorum", sessao.getIdQuorum())
                .addValue("inicioSessao", sessao.getInicioSessao())
                .addValue("fimSessao", sessao.getFimSessao())
                .addValue("totalVotosFavor", sessao.getTotalVotosFavor())
                .addValue("totalVotosContra", sessao.getTotalVotosContra())
                .addValue("idStatus", sessao.getIdStatus());

        jdbcTemplate.update(sql, parameters);
        return true;
    }
}
