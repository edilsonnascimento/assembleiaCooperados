package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllStatusQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ListAllStatusResolver implements Resolver<ListAllStatusQuery>{

    private final ReadStatusRepository repository;

    public ListAllStatusResolver(ReadStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(ListAllStatusQuery query) {
        var optional = repository.findAll();
        var listStatus = optional.orElse(new ArrayList<>());
        var listStatusDto = listStatus
                                        .stream()
                                        .map(status -> new StatusDto(status.getDescricao()))
                                        .toList();
        query.setResult(listStatusDto);
    }
}
