package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Sessao extends EntityDomain{
    private static final long LIMITE_MAXIMO_SESSAO = 60L;
    private static final long LIMITE_MINIMO_SESSAO = 1L;

    private Long id;
    private UUID uuid;
    private Long idPauta;
    private LocalDateTime inicioSessao;
    private LocalDateTime fimSessao;
    private BigDecimal totalVotosFavor;
    private BigDecimal totalVotosContra;
    private Long idStatus;

    public Sessao() {
        this.inicioSessao = LocalDateTime.now();
        this.fimSessao = LocalDateTime.now();
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
    private boolean estaAcimaLimite() {
        return MINUTES.between(inicioSessao, fimSessao)  > LIMITE_MAXIMO_SESSAO;
    }
    private boolean estaAbaixoLimite() {
        return MINUTES.between(inicioSessao, fimSessao) < LIMITE_MINIMO_SESSAO;
    }
    public boolean verificaLimite(){
        return estaAbaixoLimite() || estaAcimaLimite();
    }
}