package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.UpdateStatusCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/status")
public class WriteStatusController {

    @Autowired
    private ServiceBus serviceBus;

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated StatusDto statusDto) throws URISyntaxException {
        serviceBus.execute(new CreateStatusCommand(statusDto.descricao()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody String descricao) throws URISyntaxException{
        serviceBus.execute(new UpdateStatusCommand(id, descricao));
        return ResponseEntity.noContent().build();
    }
}
