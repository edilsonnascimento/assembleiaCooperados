package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllCooperadosQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListAllCooperadosResolver implements Resolver<ListAllCooperadosQuery> {

    @Autowired
    private ReadCooperadoRepository repository;

    @Override
    public void resolve(ListAllCooperadosQuery query) {
        var result = repository.findAll();
        query.setResult(result);
    }
}