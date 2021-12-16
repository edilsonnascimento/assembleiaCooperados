package br.org.enascimento.assembleiacooperados.red.application.resolver;

import br.org.enascimento.assembleiacooperados.red.application.query.FindByCooperadoUuidQuery;
import br.org.enascimento.assembleiacooperados.red.application.resolver.Resolver;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.CooperadoNotExistentException;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.CooperadoDto;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.COOPERADO_NOT_EXIST;

@Service
public class FindByCooperadoUuidResolver implements Resolver<FindByCooperadoUuidQuery> {

    private ReadCooperadoRepository repository;

    public FindByCooperadoUuidResolver(ReadCooperadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindByCooperadoUuidQuery query) {

        var resultOptional = repository.findByUuid(query.getUuid());

        if(!resultOptional.isPresent())
            throw new CooperadoNotExistentException(COOPERADO_NOT_EXIST);

        var result = resultOptional.get();

        query.setResult(new CooperadoDto(result.getUuid(),
                                         result.getNome(),
                                         result.getCpf()));
    }
}
