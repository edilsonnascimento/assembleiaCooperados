package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;

@Service
public class ValidaCPFConsumer {

    @Autowired
    private WebClient webClientCpf;

    public boolean isAbleToVote(String cpf) throws ValidationException {
        var mensagemErro = "";

        try {
            StatusCPF retorno = this.webClientCpf
                    .get()
                    .uri("users/{cpf}", cpf)
                    .retrieve()
                        .onStatus(HttpStatus::is4xxClientError,
                                error -> Mono.error(new RuntimeException("API not Found")))
                        .onStatus(HttpStatus::is5xxServerError,
                                error -> Mono.error(new RuntimeException("Server is not responding")))
                    .bodyToMono(StatusCPF.class)
                    .block();
            return retorno.status().equals("ABLE_TO_VOTE");
        }catch (RuntimeException exception){
            throw new ValidationException(exception.getMessage(), exception);
        }
    }

    record Body(String message){};




}
