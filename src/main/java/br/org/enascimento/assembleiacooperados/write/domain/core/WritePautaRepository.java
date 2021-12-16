package br.org.enascimento.assembleiacooperados.write.domain.core;

public interface WritePautaRepository {
    void create(Pauta pauta);
    void update(Pauta pauta);
}
