package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.ContatoDTO;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateContatoCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URISyntaxException;

import static br.org.enascimento.assembleiacooperados.common.Consts.*;

@RestController
@RequestMapping(value= PATH_CONTATOS)
public class WriteContatoController extends WriteDomainController {

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated ContatoDTO dto) throws URISyntaxException {
        serviceBus.execute(new CreateContatoCommand(dto.telefone(), dto.operadora(), dto.nome()));
        return ResponseEntity.created(new URI(PATH_CONTATOS)).build();
    }
}