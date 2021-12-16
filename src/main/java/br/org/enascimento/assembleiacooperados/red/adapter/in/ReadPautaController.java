package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.PautaInDto;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindPautaByUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllPautasQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/pautas")
public class ReadPautaController {

    @Autowired
    private ServiceBus serviceBus;

    @GetMapping
    public ResponseEntity<List<PautaInDto>> listAll(){
        var query = new ListAllPautasQuery();
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PautaInDto> findByUuid(@PathVariable UUID uuid){
        var query = new FindPautaByUuidQuery();
        query.setUuid(uuid);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
