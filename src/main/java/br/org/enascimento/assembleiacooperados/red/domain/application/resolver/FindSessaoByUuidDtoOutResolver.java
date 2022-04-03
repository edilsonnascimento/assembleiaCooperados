package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindSessaoByUuidDtoOutQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoNotExistedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.SESSAO_NOT_EXIST;

@Service
public class FindSessaoByUuidDtoOutResolver implements Resolver<FindSessaoByUuidDtoOutQuery>{

    @Autowired
     ReadSessaoRepository repository;

    @Override
    public void resolve(FindSessaoByUuidDtoOutQuery query) {
        var sessaoOutDtoOptional = repository.findByUuidReturnDto(query.getUuid());
        if(!sessaoOutDtoOptional.isPresent())
            throw new SessaoNotExistedException(SESSAO_NOT_EXIST);
        var sessaoOutDto = sessaoOutDtoOptional.get();
        query.setResult(sessaoOutDto);
    }
}