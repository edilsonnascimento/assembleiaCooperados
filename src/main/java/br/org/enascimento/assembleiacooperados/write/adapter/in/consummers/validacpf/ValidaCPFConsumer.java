package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.adapter.in.dtos.StatusCPF;
import br.org.enascimento.assembleiacooperados.write.domain.exception.ValidaCPFException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;

import static br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException.Error.CPF_INAVALID;

@Service
public class ValidaCPFConsumer{

    @Autowired
    private WebClient webClientCpf;

    public boolean isAbleToVote(String cpf) throws ValidationException {
        try {
            var statusCPF = this.webClientCpf
                    .get()
                    .uri("users/{cpf}", cpf)
                    .retrieve()
                        .onStatus(HttpStatus::is4xxClientError,
                                error -> Mono.error(new RuntimeException("API not Found")))
                        .onStatus(HttpStatus::is5xxServerError,
                                error -> Mono.error(new RuntimeException("Server is not responding")))
                    .bodyToMono(StatusCPF.class)
                    .block();
            return isAbilitadoVotar(statusCPF);
        }catch (RuntimeException exception){
            throw new ValidaCPFException(CPF_INAVALID);
        }
    }

    private boolean isAbilitadoVotar(StatusCPF retorno){
        return retorno.status().equals("ABLE_TO_VOTE");
    }
}
