package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Sessao {

    private static final long CINCO_MINUTOS = 5l;

    private Long id;
    private UUID uuid;
    private Long idPauta;
    private Long idQuorum;
    private LocalDateTime inicioSessao;
    private LocalDateTime fimSessao;
    private BigDecimal totalVotosFavor;
    private BigDecimal totalVotosContra;
    private Long idStatus;


    public Sessao() {
        this.inicioSessao = LocalDateTime.now();
        this.fimSessao = LocalDateTime.now().plusMinutes(CINCO_MINUTOS);
        this.totalVotosFavor = BigDecimal.ZERO;
        this.totalVotosContra = BigDecimal.ZERO;
        this.idStatus = 1l;
    }

    public Long getId() {
        return id;
    }

    public Sessao setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Sessao setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Sessao setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
        return this;
    }

    public Long getIdPauta() {
        return idPauta;
    }

    public LocalDateTime getInicioSessao() {
        return inicioSessao;
    }

    public Sessao setInicioSessao(LocalDateTime inicioSessao) {
        this.inicioSessao = inicioSessao;
        return this;
    }

    public LocalDateTime getFimSessao() {
        return fimSessao;
    }

    public Sessao setFimSessao(LocalDateTime fimSessao) {
        this.fimSessao = fimSessao;
        return this;
    }

    public BigDecimal getTotalVotosFavor() {
        return totalVotosFavor;
    }

    public Sessao setTotalVotosFavor(BigDecimal totalVotosFavor) {
        this.totalVotosFavor = totalVotosFavor;
        return this;
    }

    public BigDecimal getTotalVotosContra() {
        return totalVotosContra;
    }

    public Sessao setTotalVotosContra(BigDecimal totalVotosContra) {
        this.totalVotosContra = totalVotosContra;
        return this;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public Sessao setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
        return this;
    }

    public Long getIdQuorum() {
        return idQuorum;
    }

    public Sessao setIdQuorum(Long idQuorum) {
        this.idQuorum = idQuorum;
        return this;
    }
}
