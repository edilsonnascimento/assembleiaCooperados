package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.CommandBus;
import br.org.enascimento.assembleiacooperados.write.domain.application.CreatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.UpdatePautaCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("v1/pautas")
public class WritePautaController {

    @Autowired
    private CommandBus commandBusbus;

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated CreatePautaCommand commad) throws URISyntaxException {
        commandBusbus.execute(commad);
        return (ResponseEntity) ResponseEntity.created(new URI("v1/pautas/"+commad.uuid())).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity update(@PathVariable UUID uuid, @RequestBody Map<String, Object> payload){
        var command = new UpdatePautaCommand(uuid, (String) payload.get("titulo"), (String) payload.get("descricao"));
        commandBusbus.execute(command);
        return ResponseEntity.noContent().build();
    }
}