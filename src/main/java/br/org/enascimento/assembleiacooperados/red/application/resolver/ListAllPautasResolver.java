package br.org.enascimento.assembleiacooperados.red.application.resolver;

import br.org.enascimento.assembleiacooperados.red.application.query.ListAllPautasQuery;
import br.org.enascimento.assembleiacooperados.red.application.resolver.Resolver;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListAllPautasResolver implements Resolver<ListAllPautasQuery> {

    @Autowired
    private ReadPautaRepository repository;

    @Override
    public void resolve(ListAllPautasQuery query) {
        var result = repository.findAll();
        query.setResult(result);
    }
}
