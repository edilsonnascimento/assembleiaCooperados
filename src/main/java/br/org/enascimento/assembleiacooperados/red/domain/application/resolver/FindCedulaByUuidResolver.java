package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCedulaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadUrnaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CEDULA_NOT_EXIST;

@Service
public class FindCedulaByUuidResolver implements Resolver<FindCedulaByUuidQuery>{

    private ReadUrnaRepository repository;

    public FindCedulaByUuidResolver(ReadUrnaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindCedulaByUuidQuery query) {
        var optionalDto = repository.findByUuidDto(query.getUuid());
        if(!optionalDto.isPresent()) throw new CedulaNotExistentException(CEDULA_NOT_EXIST);
        var dto = optionalDto.get();
        query.setResult(dto);
    }
}
