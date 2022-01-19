package br.org.enascimento.assembleiacooperados.red.adapter.out;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import br.org.enascimento.assembleiacooperados.write.domain.core.Cedula;
import br.org.enascimento.assembleiacooperados.write.domain.core.Voto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ReadCedulaRepositoryImpl implements ReadUrnaRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public ReadCedulaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Cedula> findByUuid(UUID uuid) {
        var sql = """
                SELECT id, uuid, id_sessao, id_cooperado, voto, created_at, updated_at
                FROM cedula
                WHERE uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                var urna = new Cedula();
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
    public Optional<CedulaOutDto> findByUuidDto(UUID uuid) {
        var sql = """
               SELECT ced.uuid AS uuid_cedula,
                      ses.uuid AS uuid_sessao,
                      cop.uuid AS uuid_cooperado,
                      ced.voto,
                      ced.created_at AS data_voto
               FROM cedula AS ced
                        JOIN cooperado AS cop ON cop.id = ced.id_cooperado
                        JOIN sessao AS ses ON ses.id = ced.id_sessao
                WHERE ced.uuid = :uuid""";

        var parameters = new MapSqlParameterSource()
                .addValue("uuid", uuid);

        return jdbcTemplate.query(sql, parameters, resultSet -> {
            if (resultSet.next()) {
                return Optional.of(new CedulaOutDto().
                        setUuidCedula(UUID.fromString(resultSet.getString("uuid_cedula"))).
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
