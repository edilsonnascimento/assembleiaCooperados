package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllCedulaQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCedulaRepository;
import br.org.enascimento.assembleiacooperados.red.domain.exception.CedulaNotExistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CEDULA_NOT_EXIST;

@Service
public class ListAllCedulaResolver implements Resolver<ListAllCedulaQuery> {

    @Autowired
    private ReadCedulaRepository repositoy;

    @Override
    public void resolve(ListAllCedulaQuery query) {
        var listagemOptional = repositoy.findAll();
        if(!listagemOptional.isPresent())
            throw new CedulaNotExistentException(CEDULA_NOT_EXIST);
        query.setResult(listagemOptional.get());
    }
}