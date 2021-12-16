package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindPautaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadPautaRepository;
import org.springframework.stereotype.Service;

@Service
public class FindPautaByUuidResolver implements Resolver<FindPautaByUuidQuery>{

    private ReadPautaRepository repository;

    public FindPautaByUuidResolver(ReadPautaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindPautaByUuidQuery query) {
        var pauta = repository.findByUuid(query.getUuid()).get();
        var pautaInDto = new PautaInDto(pauta.getUuid(), pauta.getTitulo(), pauta.getDescricao());
        query.setResult(pautaInDto);
    }
}
