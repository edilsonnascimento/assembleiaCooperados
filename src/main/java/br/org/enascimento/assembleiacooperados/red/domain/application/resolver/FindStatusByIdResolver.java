package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindStatusByIdQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;
import org.springframework.stereotype.Service;

@Service
public class FindStatusByIdResolver implements Resolver<FindStatusByIdQuery>{


    private final ReadStatusRepository repository;

    public FindStatusByIdResolver(ReadStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindStatusByIdQuery query) {
        var statusOptional = repository.findById(query.getId());
        var status = statusOptional.get();
        var statusDto = new StatusDto(status.getDescricao());
        query.setResult(statusDto);
    }
}
