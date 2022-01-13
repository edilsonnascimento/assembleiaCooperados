package br.org.enascimento.assembleiacooperados.red.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.DomainController;
import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.SessaoOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindSessaoByUuidDtoOutQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/sessoes")
public class ReadSessaoController extends DomainController {

    @GetMapping("/{uuid}")
    public ResponseEntity<SessaoOutDto> findByUuid(@PathVariable UUID uuid){
        var query = new FindSessaoByUuidDtoOutQuery();
        query.setUuid(uuid);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
