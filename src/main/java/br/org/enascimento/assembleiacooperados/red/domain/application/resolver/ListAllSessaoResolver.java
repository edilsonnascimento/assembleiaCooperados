package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllSessaoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ListAllSessaoResolver implements Resolver<ListAllSessaoQuery>{

    private final ReadSessaoRepository repository;

    public ListAllSessaoResolver(ReadSessaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(ListAllSessaoQuery query) {
        var listaOptional = repository.findAll();
        query.setResult(listaOptional.get());
    }
}
