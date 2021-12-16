package br.org.enascimento.assembleiacooperados.red.application.resolver;

import br.org.enascimento.assembleiacooperados.red.application.query.Query;

public interface Resolver<T extends Query> {
    void resolve(T query);
}
