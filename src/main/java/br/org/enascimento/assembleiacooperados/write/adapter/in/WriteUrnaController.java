package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaInDto;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.UrnaIntoDto;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateStatusCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.command.CreateUrnaCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/urnas")
public class WriteUrnaController extends WriteDomainController{

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Validated UrnaIntoDto urnaDto) throws URISyntaxException {
        serviceBus.execute(new CreateUrnaCommand(
                                urnaDto.uuidUrna(),
                                urnaDto.uuidSessao(),
                                urnaDto.uuidCooperado(),
                                urnaDto.voto()));
        return ResponseEntity.created(new URI("/v1/urnas/" + urnaDto.uuidUrna())).build();
    }
}
