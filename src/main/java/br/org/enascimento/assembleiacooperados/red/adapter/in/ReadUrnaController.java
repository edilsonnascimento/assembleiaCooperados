package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.common.DomainController;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.UrnaOutDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindUrnaByUuidQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/urnas")
public class ReadUrnaController extends DomainController {

    @GetMapping("/{uuid}")
    public ResponseEntity<UrnaOutDto> findByUuid(@PathVariable UUID uuid){
        var query = new FindUrnaByUuidQuery();
        query.setUuid(uuid);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
