package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Sessao;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadSessaoRepositoryImpl implements ReadSessaoRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadSessaoRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Sessao> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, id_pauta, id_urna, inicio_sessao, fim_sessao, 
                       total_votos_favor, total_votos_contra, created_at, updated_at, id_status
                FROM sessao
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, result -> {
            if (result.next()) {
                return Optional.of(new Sessao().
                        setId(result.getLong("id")).
                        setUuid(UUID.fromString(result.getString("uuid"))).
                        setIdPauta(result.getLong("id_pauta")).
                        setIdUrna(result.getLong("id_urna")==0?null:result.getLong("id_urna")).
                        setInicioSessao(result.getTimestamp("inicio_sessao").toLocalDateTime()).
                        setFimSessao(result.getTimestamp("fim_sessao").toLocalDateTime()).
                        setTotalVotosFavor(BigDecimal.valueOf(result.getInt("total_votos_favor"))).
                        setTotalVotosContra(BigDecimal.valueOf(result.getInt("total_votos_contra"))).
                        setCreatedAt(result.getTimestamp("created_at").toLocalDateTime()).
                        setUpdatedAt(result.getTimestamp("updated_at").toLocalDateTime()).
                        setIdStatus(result.getLong("id_status"))
                );

            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<SessaoOutDto> findByUuidReturnDto(UUID uuid) {
        var sql = """
                SELECT ses.uuid AS uuid_sessao,
                       pau.titulo AS titulo_pauta,
                       pau.descricao AS descricao_pauta,
                       ses.inicio_sessao AS data_inicio_sessao,
                       ses.fim_sessao AS data_fim_sessao,
                       ses.total_votos_favor AS quantiade_votos_favoraveis,
                       ses.total_votos_contra AS quantiade_votos_contra,
                       sta.descricao AS estado_sessao,
                       COUNT(urn.id) AS quantidade_participantes
                FROM sessao AS ses
                     LEFT JOIN urna AS urn ON urn.id = ses.id_urna
                     LEFT JOIN pauta AS pau ON pau.id = ses.id_pauta
                     LEFT JOIN status AS sta ON sta.id = ses.id_status
                WHERE ses.uuid = :uuid
                GROUP BY ses.uuid, 
                         pau.titulo, 
                         pau.descricao, 
                         ses.inicio_sessao,
                         ses.fim_sessao, 
                         ses.total_votos_favor, 
                         ses.total_votos_contra,
                         sta.descricao""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, result -> {
            if (result.next()) {
                return Optional.of(new SessaoOutDto(
                            UUID.fromString(result.getString("uuid_sessao")),
                            BigDecimal.valueOf(result.getInt("quantidade_participantes")),
                            result.getString("titulo_pauta"),
                            result.getString("descricao_pauta"),
                            result.getTimestamp("data_inicio_sessao").toLocalDateTime(),
                            result.getTimestamp("data_fim_sessao").toLocalDateTime(),
                            BigDecimal.valueOf(result.getInt("quantiade_votos_favoraveis")),
                            BigDecimal.valueOf(result.getInt("quantiade_votos_contra")),
                            result.getString("estado_sessao")));
            }
            return Optional.empty();
        });
    }


}
