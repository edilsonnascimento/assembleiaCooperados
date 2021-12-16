package br.org.enascimento.assembleiacooperados.write.domain.core;

public interface WriteCooperadoRepository {
    void create(Cooperado capture);
    void update(Cooperado cooperado);
}
