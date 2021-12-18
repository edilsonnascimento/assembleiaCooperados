package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.FindStatusByIdQuery;
import br.org.enascimento.assembleiacooperados.red.domain.application.query.ListAllStatusQuery;
import br.org.enascimento.assembleiacooperados.write.adapter.in.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/status")
public class ReadStatusController {

    @Autowired
    private ServiceBus serviceBus;

    @GetMapping("/{id}")
    public ResponseEntity<StatusDto> findById(@PathVariable Long id){
        var query = new FindStatusByIdQuery();
        query.setId(id);
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }

    @GetMapping
    public ResponseEntity<List<StatusDto>> findAll(){
        var query = new ListAllStatusQuery();
        serviceBus.execute(query);
        return ResponseEntity.ok(query.getResult());
    }
}
