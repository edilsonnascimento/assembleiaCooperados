package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dtos.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CooperadoDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCooperadoCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateCooperadoCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("v1/cooperados")
public class WriteCooperadoController {

    @Autowired
    private ServiceBus serviceBus;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated CooperadoDto cooperadoDto) throws URISyntaxException {

        serviceBus.execute(new CreateCooperadoCommand(cooperadoDto.uuid(),
                                                     cooperadoDto.nome(),
                                                     cooperadoDto.cpf()));

        return ResponseEntity.created(new URI("v1/cooperados/" + cooperadoDto.uuid())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> update(@PathVariable UUID uuid, @RequestBody CooperadoInDto cooperadoInDto){

        serviceBus.execute(new UpdateCooperadoCommand(uuid,
                                                      cooperadoInDto.nome(),
                                                      cooperadoInDto.cpf()));
        return ResponseEntity.noContent().build();
    }

}