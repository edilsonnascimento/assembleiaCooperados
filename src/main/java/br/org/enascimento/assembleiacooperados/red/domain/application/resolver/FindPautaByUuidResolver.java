package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindPautaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.PautaNotExistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.PAUTA_NOT_EXIST;

@Service
public class FindPautaByUuidResolver implements Resolver<FindPautaByUuidQuery>{

    @Autowired
    private ReadPautaRepository repository;

    @Override
    public void resolve(FindPautaByUuidQuery query) {
        var pautaOptional = repository.findByUuid(query.getUuid());

        if(!pautaOptional.isPresent())
            throw new PautaNotExistentException(PAUTA_NOT_EXIST);
        var pauta = pautaOptional.get();
        var pautaInDto = new PautaInDto(pauta.getUuid(), pauta.getTitulo(), pauta.getDescricao());
        query.setResult(pautaInDto);
    }
}