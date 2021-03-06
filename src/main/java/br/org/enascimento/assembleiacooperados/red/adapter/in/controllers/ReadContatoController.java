package br.org.enascimento.assembleiacooperados.red.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.red.adapter.out.dtos.ContatoOutDTO;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindContatoByCodigoDTOOutQuery;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllContatoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static br.org.enascimento.assembleiacooperados.common.Consts.PATH_CONTATOS;

@RestController
@RequestMapping(PATH_CONTATOS)
public class ReadContatoController {

    @Autowired
    private ServiceBus serviceBus;

    @GetMapping
    public ResponseEntity<List<ContatoOutDTO>> listAll() {
        var query = new ListAllContatoQuery();
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
    @GetMapping("/{codigo}")
    public ResponseEntity<ContatoOutDTO> contatoPor(@PathVariable String codigo) {
        var query = new FindContatoByCodigoDTOOutQuery();
        query.setCodigo(codigo);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }

}
