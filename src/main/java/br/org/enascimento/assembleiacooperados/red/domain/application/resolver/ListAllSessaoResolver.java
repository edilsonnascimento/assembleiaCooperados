package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllSessaoQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadSessaoRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.SessaoNotExistedException;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.SESSAO_NOT_EXIST;

@Service
public class ListAllSessaoResolver implements Resolver<ListAllSessaoQuery>{

    private final ReadSessaoRepository repository;

    public ListAllSessaoResolver(ReadSessaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(ListAllSessaoQuery query) {
        var listaOptional = repository.findAll();
        if(!listaOptional.isPresent())
            throw new SessaoNotExistedException(SESSAO_NOT_EXIST);
        query.setResult(listaOptional.get());
    }
}
