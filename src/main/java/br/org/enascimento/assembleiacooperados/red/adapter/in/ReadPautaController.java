package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadPautaRepositoryImpl;
import br.org.enascimento.assembleiacooperados.red.domain.core.PautaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/pautas")
public class ReadPautaController {

    @Autowired
    private ReadPautaRepositoryImpl respository;

    @GetMapping
    public ResponseEntity<List<PautaDto>> listAll(){
        return ResponseEntity.ok(respository.findAll());
    }
}
