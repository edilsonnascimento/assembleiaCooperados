package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
