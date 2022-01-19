package br.org.enascimento.assembleiacooperados.red.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.DomainController;
import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.CedulaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindCedulaByUuidQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/cedulas")
public class ReadCedulaController extends DomainController {

    @GetMapping("/{uuid}")
    public ResponseEntity<CedulaOutDto> findByUuid(@PathVariable UUID uuid){
        var query = new FindCedulaByUuidQuery();
        query.setUuid(uuid);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
