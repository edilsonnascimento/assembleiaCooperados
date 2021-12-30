package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Sessao {

    private static final long CINCO_MINUTOS = 5l;

    private Long id;
    private UUID uuid;
    private Long idPauta;
    private Long idUrna;
    private LocalDateTime inicioSessao;
    private LocalDateTime fimSessao;
    private BigDecimal totalVotosFavor;
    private BigDecimal totalVotosContra;
    private Long idStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Long getIdUrna() {
        return idUrna;
    }

    public Sessao setIdUrna(Long idUrna) {
        this.idUrna = idUrna;
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


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Sessao setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Sessao setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
