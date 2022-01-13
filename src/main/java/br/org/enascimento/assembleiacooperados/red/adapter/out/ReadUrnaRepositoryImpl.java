package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Urna;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadUrnaRepositoryImpl implements ReadUrnaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadUrnaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Urna> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, id_sessao, id_cooperado, voto, created_at, updated_at
                FROM urna
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var urna = new Urna();
                urna.setId(resultSet.getLong("id"));
                urna.setUuid(UUID.fromString(resultSet.getString("uuid")));
                urna.setIdSessao(resultSet.getLong("id_sessao"));
                urna.setIdCoopereado(resultSet.getLong("id_cooperado"));
                urna.setVoto(Voto.valueOf(resultSet.getString("voto")));
                urna.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                urna.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                return Optional.of(urna);
            }
            return Optional.empty();
        });
    }

    @Override
    public Optional<UrnaOutDto> findByUuidDto(UUID uuid) {
        var sql = """
               SELECT urn.uuid AS uuid_urna,
                      ses.uuid AS uuid_sessao,
                      cop.uuid AS uuid_cooperado,
                      urn.voto,
                      urn.created_at AS data_voto
               FROM urna AS urn
                        JOIN cooperado AS cop ON cop.id = urn.id_cooperado
                        JOIN sessao AS ses ON ses.id = urn.id_sessao
                WHERE urn.uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new UrnaOutDto().
                        setUuidUrna(UUID.fromString(resultSet.getString("uuid_urna"))).
                        setUuidSessao(UUID.fromString(resultSet.getString("uuid_sessao"))).
                        setUuidCooperado(UUID.fromString(resultSet.getString("uuid_cooperado"))).
                        setVoto(Voto.valueOf(resultSet.getString("voto"))).
                        setDataVoto(resultSet.getTimestamp("data_voto").toLocalDateTime())
                );

            }
            return Optional.empty();
        });
    }
}
