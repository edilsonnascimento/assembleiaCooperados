package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindContatoByCodigoDTOOutQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadContatoRepository;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ContatoExcepetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CONTATO_NOT_EXIST;

@Service
public class FindContatoByCodigoDTOOutResolver implements Resolver<FindContatoByCodigoDTOOutQuery>{

    @Autowired
    private ReadContatoRepository repository;

    @Override
    public void resolve(FindContatoByCodigoDTOOutQuery query) {
        var optinalContato = repository.findByCodigo(query.getCodigo());
        if(optinalContato.isEmpty())
            throw new ContatoExcepetion(CONTATO_NOT_EXIST);
        var contato = optinalContato.get();
        var contatoOutDTO = new ContatoOutDTO(contato.getNome(), contato.getTelefone(), contato.getOperadora(), contato.getCreatedAt(), contato.getCodigo());
        query.setResult(contatoOutDTO);
    }
}