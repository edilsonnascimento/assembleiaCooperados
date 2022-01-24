package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllCedulaQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCedulaRepository;
import org.springframework.stereotype.Service;

@Service
public class ListAllCedulaResolver implements Resolver<ListAllCedulaQuery> {

    private final ReadCedulaRepository repositoy;

    public ListAllCedulaResolver(ReadCedulaRepository repository) {
        this.repositoy = repository;
    }

    @Override
    public void resolve(ListAllCedulaQuery query) {
        var listagemOptional = repositoy.findAll();
        query.setResult(listagemOptional.get());
    }
}
