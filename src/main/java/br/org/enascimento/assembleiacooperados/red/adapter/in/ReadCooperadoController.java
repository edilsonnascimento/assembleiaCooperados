package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.red.adapter.in.dto.CooperadoInDto;
import br.org.enascimento.assembleiacooperados.red.application.FindByCooperadoUuidQuery;
import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.CooperadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/cooperados")
public class ReadCooperadoController {

    @Autowired
    private ReadCooperadoRepository respository;

    @Autowired
    private ServiceBus serviceBus;

    @GetMapping
    public ResponseEntity<List<CooperadoInDto>> listAll(){
        return ResponseEntity.ok(respository.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CooperadoDto> findUuid(@PathVariable UUID uuid) {
        var query = new FindByCooperadoUuidQuery();
        query.setUuid(uuid);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
