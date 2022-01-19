package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCedulaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import org.springframework.stereotype.Service;

@Service
public class FindCedulaByUuidResolver implements Resolver<FindCedulaByUuidQuery>{

    private ReadUrnaRepository repository;

    public FindCedulaByUuidResolver(ReadUrnaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindCedulaByUuidQuery query) {
        var optionalDto = repository.findByUuidDto(query.getUuid());
        var dto = optionalDto.get();
        query.setResult(dto);
    }
}
