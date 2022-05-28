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
        super();
        inicializaVariaveisMembro();
    }

    public Sessao(Long id, UUID uuid, Long idPauta, Long idStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.uuid = uuid;
        this.idPauta = idPauta;
        this.idStatus = idStatus;
        inicializaVariaveisMembro();
    }
    private void inicializaVariaveisMembro(){
        this.inicioSessao = LocalDateTime.now();
        this.fimSessao = LocalDateTime.now();
        this.totalVotosFavor = BigDecimal.ZERO;
        this.totalVotosContra = BigDecimal.ZERO;
        this.idStatus = 1l;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public Long getIdPauta() {
        return idPauta;
    }
    public void setIdPauta(Long idPauta) {
        this.idPauta = idPauta;
    }
    public LocalDateTime getInicioSessao() {
        return inicioSessao;
    }
    public void setInicioSessao(LocalDateTime inicioSessao) {
        this.inicioSessao = inicioSessao;
    }
    public LocalDateTime getFimSessao() {
        return fimSessao;
    }
    public void setFimSessao(LocalDateTime fimSessao) {
        this.fimSessao = fimSessao;
    }
    public BigDecimal getTotalVotosFavor() {
        return totalVotosFavor;
    }
    public void setTotalVotosFavor(BigDecimal totalVotosFavor) {
        this.totalVotosFavor = totalVotosFavor;
    }
    public BigDecimal getTotalVotosContra() {
        return totalVotosContra;
    }
    public void setTotalVotosContra(BigDecimal totalVotosContra) {
        this.totalVotosContra = totalVotosContra;
    }
    public Long getIdStatus() {
        return idStatus;
    }
    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
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