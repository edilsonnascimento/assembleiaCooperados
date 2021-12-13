package br.org.enascimento.assembleiacooperados.red.observable;

import br.org.enascimento.assembleiacooperados.common.InternalEvent;
import br.org.enascimento.assembleiacooperados.red.application.Query;
import br.org.enascimento.assembleiacooperados.write.domain.exception.DomainException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class QueryEvent extends InternalEvent {

    private final Query query;

    public QueryEvent(Query query) {
        startTimer();
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public Object getSource() {
        return getQuery();
    }

    @Override
    public String toJson() {

        try {
            var mapper = new ObjectMapper();
            Map<String, Object> message = new HashMap<>(Map.of("event", getOrigin()));
            message.put("content", getQuery());
            message.put("elapsedTimeInMilli", getElapsedTimeInMilli());

            if (hasError()) {
                message.put("message", getException().getMessage());

                if (getException() instanceof DomainException domainException && domainException.hasError()) {
                    message.put("errors", domainException.getErrors().toString());
                }
            }

            return mapper.writeValueAsString(message);

        } catch (JsonProcessingException jsonException) {
            return String.format("%s - %s", query, jsonException);
        }
    }

}
