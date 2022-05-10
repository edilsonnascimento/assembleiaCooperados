package br.org.enascimento.assembleiacooperados.red.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.DomainController;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllOperadoraQuery;
import br.org.enascimento.assembleiacooperados.write.domain.core.Operadora;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.org.enascimento.assembleiacooperados.common.Consts.PATH_OPERADORAS;

@RestController
@RequestMapping(PATH_OPERADORAS)
public class ReadOperadoraController extends DomainController {

    @GetMapping()
    public ResponseEntity<List<Operadora>> findAll(){
        var query = new ListAllOperadoraQuery();
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}