package br.org.enascimento.assembleiacooperados.write.adapter.out;

import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.Status;
import br.org.enascimento.assembleiacooperados.write.domain.core.WriteSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.INVALID_DUPLICATE_DATA;

@Repository
public class WriteSessaoRepositoryImpl implements WriteSessaoRepository {

    private final static Long STATUS_SESSAO = 2L;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public WriteSessaoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(Sessao sessao) {
        try {
            var sql = """
                      INSERT INTO sessao(
                              uuid,
                              id_pauta,
                              inicio_sessao,
                              fim_sessao,
                              total_votos_favor,
                              total_votos_contra,
                              id_status)
                      values (                      
                              :uuid,
                              :idPauta,
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
                    .addValue("inicioSessao", sessao.getInicioSessao())
                    .addValue("fimSessao", sessao.getFimSessao())
                    .addValue("totalVotosFavor", sessao.getTotalVotosFavor())
                    .addValue("totalVotosContra", sessao.getTotalVotosContra())
                    .addValue("idStatus", sessao.getIdStatus());

            jdbcTemplate.update(sql, parameters);
        } catch (DuplicateKeyException exception) {
            var duplicateException = new DuplicatedDataException(INVALID_DUPLICATE_DATA, exception);
            throw duplicateException;
        }
        return true;
    }

    @Override
    public Optional<Status> findStatus(Long id) {
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

    @Override
    public void fecharSessoes() {
        var idsSessoesVencidas = findSessoesInvalidates();
        for (Long idSessao : idsSessoesVencidas) fecharSessao(idSessao);
    }

    private void fecharSessao(Long idSessao) {
        var sqlVotosContra = """                
            SELECT voto, COUNT(1) AS quantidade_votos
            FROM cedula
            WHERE id_sessao = :idSessao AND voto = 'CONTRA'
            GROUP BY voto""";

        var paramVotosContra = new MapSqlParameterSource()
                .addValue("idSessao", idSessao);
        var votosContra =
                jdbcTemplate.query(sqlVotosContra, paramVotosContra, resultSet -> {
                    if (resultSet.next()) return resultSet.getInt("quantidade_votos");
                    return Integer.valueOf(0);
                });

        var sqlVotosFavoraveis = """                
            SELECT voto, COUNT(1) AS quantidade_votos
            FROM cedula
            WHERE id_sessao = :idSessao AND voto = 'FAVORAVEL'
            GROUP BY voto""";
        var paramVotosFavoraveis = new MapSqlParameterSource()
                .addValue("idSessao", idSessao);
        var votosFavoraveis =
                jdbcTemplate.query(sqlVotosFavoraveis, paramVotosFavoraveis, resultSet -> {
                    if (resultSet.next()) return resultSet.getInt("quantidade_votos");
                    return Integer.valueOf(0);
                });

        var sql = """
                  UPDATE sessao
                  SET id_status = :statusSessao, 
                      total_votos_favor = :votosFavoraveis, 
                      total_votos_contra = :votosContra
                  WHERE id = :idSessao
                  """;
        var parameters = new MapSqlParameterSource()
                .addValue("statusSessao", STATUS_SESSAO)
                .addValue("votosFavoraveis", votosFavoraveis)
                .addValue("votosContra", votosContra)
                .addValue("idSessao", idSessao);
        jdbcTemplate.update(sql, parameters);
    }

    private List<Long> findSessoesInvalidates() {
        var sql =
                """
                    SELECT id
                    FROM sessao AS ses
                    WHERE id_status <> :statusSessao AND
                          (now() AT TIME ZONE 'America/Sao_Paulo') > fim_sessao;""";
        var parameters = new MapSqlParameterSource()
                .addValue("statusSessao", STATUS_SESSAO);

        return jdbcTemplate.query(sql, parameters, (resultSet, rowNum) -> resultSet.getLong("id"));
    }
}