package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.CedulaDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateCedulaCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/cedulas/")
public class WriteCedulaController extends WriteDomainController{

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated CedulaDto urnaDto) throws URISyntaxException {
        serviceBus.execute(new CreateCedulaCommand(
                                urnaDto.uuidCedula(),
                                urnaDto.uuidSessao(),
                                urnaDto.uuidCooperado(),
                                urnaDto.voto()));
        return ResponseEntity.created(new URI("/v1/cedulas/" + urnaDto.uuidCedula())).build();
    }
}
