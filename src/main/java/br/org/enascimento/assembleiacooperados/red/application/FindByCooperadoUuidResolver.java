package br.org.enascimento.assembleiacooperados.red.application;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.CooperadoDto;
import org.springframework.stereotype.Service;

@Service
public class FindByCooperadoUuidResolver implements Resolver<FindByCooperadoUuidQuery>{

    private ReadCooperadoRepository repository;

    public FindByCooperadoUuidResolver(ReadCooperadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void resolve(FindByCooperadoUuidQuery query) {
        var result = repository.findByUuid(query.getUuid()).get();
        query.setResult(new CooperadoDto(result.getUuid(),
                                         result.getNome(),
                                         result.getCpf()));
    }
}
