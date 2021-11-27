package br.org.enascimento.assembleiacooperados.write.adapter.in;

import br.org.enascimento.assembleiacooperados.write.domain.application.CreatePautaCommand;
import br.org.enascimento.assembleiacooperados.write.domain.application.CreatePautaCommandHandler;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DuplicatedDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("v1/pautas")
public class PautaController {

    @Autowired
    private CreatePautaCommandHandler handler;

    @PostMapping
    public ResponseEntity create(@RequestBody @Validated CreatePautaCommand commad) throws URISyntaxException {

        try {
            handler.handle(commad);
        }catch (DuplicatedDataException exception){
            return (ResponseEntity) ResponseEntity.badRequest().body(exception.getMessage());
        }
        return (ResponseEntity) ResponseEntity.created(new URI("v1/pautas/"+commad.uuid())).build();
    }
}