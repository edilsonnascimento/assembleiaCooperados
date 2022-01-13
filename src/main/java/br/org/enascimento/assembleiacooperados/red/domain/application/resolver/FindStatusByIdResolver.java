package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindStatusByIdQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadStatusRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.StatusNotExistedExcepetion;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusDto;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.STATUS_NOT_EXIST;

@Service
public class FindStatusByIdResolver implements Resolver<FindStatusByIdQuery>{


    private final ReadStatusRepository repository;

    public FindStatusByIdResolver(ReadStatusRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindStatusByIdQuery query) {
        var statusOptional = repository.findById(query.getId());

        if(!statusOptional.isPresent())
            throw new StatusNotExistedExcepetion(STATUS_NOT_EXIST);

        var status = statusOptional.get();
        var statusDto = new StatusDto(status.getDescricao());
        query.setResult(statusDto);
    }
}
