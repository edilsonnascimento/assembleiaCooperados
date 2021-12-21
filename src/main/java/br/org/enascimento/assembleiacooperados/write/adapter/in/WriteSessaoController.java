package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.SessaoInDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateSessaoCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/sessao")
public class WriteSessaoController extends WriteDomainController{

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated SessaoInDto dto) throws URISyntaxException {
        serviceBus.execute(new CreateSessaoCommand(dto));
        return ResponseEntity.created(new URI("v1/sessao/" + dto.uuid())).build();
    }
}
