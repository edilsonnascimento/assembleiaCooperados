package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.PautaDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdatePautaCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("v1/pautas")
public class WritePautaController {

    @Autowired
    private ServiceBus serviceBus;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated PautaDto pautaDto) throws URISyntaxException {

        serviceBus.execute(new CreatePautaCommand(pautaDto.uuid(),
                                                     pautaDto.titulo(),
                                                     pautaDto.descricao()));

        return ResponseEntity.created(new URI("v1/pautas/"+ pautaDto.uuid())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<String> update(@PathVariable UUID uuid, @RequestBody PautaDto pautaDto){

        serviceBus.execute(new UpdatePautaCommand(uuid,
                                                     pautaDto.titulo(),
                                                     pautaDto.descricao()));

        return ResponseEntity.noContent().build();
    }
}