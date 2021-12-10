package br.org.enascimento.assembleiacooperados.red.adapter.in;

import br.org.enascimento.assembleiacooperados.red.domain.core.ReadCooperadoRepository;
import br.org.enascimento.assembleiacooperados.write.adapter.in.CooperadoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/cooperados")
public class ReadCooperadoController {

    @Autowired
    private ReadCooperadoRepository respository;

    @GetMapping
    public ResponseEntity<List<CooperadoDto>> listAll(){
        return ResponseEntity.ok(respository.findAll());
    }
}
