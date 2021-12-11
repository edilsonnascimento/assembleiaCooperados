package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.CommandBus;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.CooperadoDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.CreateCooperadoCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/cooperados")
public class WriteCooperadoController {

    @Autowired
    private CommandBus commandBusbus;

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated CooperadoDto cooperadoDto) throws URISyntaxException {

        commandBusbus.execute(new CreateCooperadoCommand(cooperadoDto.uuid(),
                                                     cooperadoDto.nome(),
                                                     cooperadoDto.cpf()));

        return (ResponseEntity) ResponseEntity.created(new URI("v1/cooperados/" + cooperadoDto.uuid())).build();
    }

}