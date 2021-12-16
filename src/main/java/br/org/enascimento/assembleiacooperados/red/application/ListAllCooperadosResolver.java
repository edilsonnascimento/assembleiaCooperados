package br.org.enascimento.assembleiacooperados.red.application;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import org.springframework.stereotype.Service;

@Service
public class ListAllCooperadosResolver implements Resolver<ListAllCooperadosQuery>{

    private final ReadCooperadoRepository repository;

    public ListAllCooperadosResolver(ReadCooperadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(ListAllCooperadosQuery query) {

        var result = repository.findAll();

        query.setResult(result);
    }
}