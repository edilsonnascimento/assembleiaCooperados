package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.Query;

public interface Resolver<T extends Query> {
    void resolve(T query);
}
