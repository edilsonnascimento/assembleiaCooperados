package br.org.enascimento.assembleiacooperados.write.adapter.in.consummers.validacpf;

import br.org.enascimento.assembleiacooperados.write.domain.core.ValidaCPF;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class ValidaCpfConfig{
    private static final String BASE_URL = "https://user-info.herokuapp.com/";
    public static final int TIMEOUT = 1000;

    @Bean
    public WebClient webClientCpf(WebClient.Builder builder){

        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                });
        return builder
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    @Primary
    @Bean
    public ValidaCPFServiceAdapter validaCPFServiceAdapter(){
        return new ValidaCPFService();
    }

    @Primary
    @Bean
    public ValidaCPF validaCPF(){
        return new ValidaCPFImpl();
    }
}