package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusInDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateStatusCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/status")
public class WriteStatusController {

    @Autowired
    private ServiceBus serviceBus;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated StatusDto statusDto) throws URISyntaxException {
        serviceBus.execute(new CreateStatusCommand(statusDto.descricao()));
        return ResponseEntity.created(new URI("v1/sessao/")).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Validated StatusInDto dto){
        serviceBus.execute(new UpdateStatusCommand(id, dto.descricao()));
        return ResponseEntity.noContent().build();
    }
}
