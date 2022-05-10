package br.org.enascimento.assembleiacooperados.red.domain.application.resolver;

import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllOperadoraQuery;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllOperadoraResolver implements Resolver<ListAllOperadoraQuery>{
    @Override
    public void resolve(ListAllOperadoraQuery query) {
        query.setResult(List.of(Operadora.values()));
    }
}