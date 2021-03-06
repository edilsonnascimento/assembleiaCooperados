package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCooperadoByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CooperadoNotExistentException;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CooperadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.COOPERADO_NOT_EXIST;

@Service
public class FindCooperadoByUuidResolver implements Resolver<FindCooperadoByUuidQuery> {

    @Autowired
    private ReadCooperadoRepository repository;

    @Override
    public void resolve(FindCooperadoByUuidQuery query) {
        var resultOptional = repository.findByUuid(query.getUuid());
        if(!resultOptional.isPresent())
            throw new CooperadoNotExistentException(COOPERADO_NOT_EXIST);
        var result = resultOptional.get();
        query.setResult(new CooperadoDto(result.getUuid(),
                                         result.getNome(),
                                         result.getCpf()));
    }
}