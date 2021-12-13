package br.org.enascimento.assembleiacooperados.red.application;

public interface Resolver<T extends Query> {
    void resolve(T query);
}
