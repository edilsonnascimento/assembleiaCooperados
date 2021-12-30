package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindUrnaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import org.springframework.stereotype.Service;

@Service
public class FindUrnaByUuidResolver implements Resolver<FindUrnaByUuidQuery>{

    private ReadUrnaRepository repository;

    public FindUrnaByUuidResolver(ReadUrnaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindUrnaByUuidQuery query) {
        var optionalDto = repository.findByUuidDto(query.getUuid());
        var dto = optionalDto.get();
        query.setResult(dto);
    }
}
