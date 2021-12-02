package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.red.adapter.out.PautaDto;
import br.org.enascimento.assembleiacooperados.red.adapter.out.ReadPautaRespositoryImpl;
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
    private ReadPautaRespositoryImpl respository;

    @GetMapping
    public ResponseEntity<List<PautaDto>> getAll(){
        return ResponseEntity.ok(respository.findAll());
    }
}
