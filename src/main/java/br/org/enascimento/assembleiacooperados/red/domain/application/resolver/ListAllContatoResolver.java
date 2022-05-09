package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllContatoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CONTATO_NOT_EXIST;

@Service
public class ListAllContatoResolver implements Resolver<ListAllContatoQuery>{

    @Autowired
    private ReadContatoRepository repository;

    @Override
    public void resolve(ListAllContatoQuery query) {
        var optionalDto = repository.findAll();
        if(optionalDto.isEmpty())
            throw new CedulaNotExistentException(CONTATO_NOT_EXIST);
        query.setResult(optionalDto.get());
    }
}
