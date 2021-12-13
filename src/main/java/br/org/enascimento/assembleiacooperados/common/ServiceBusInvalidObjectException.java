package br.org.enascimento.assembleiacooperados.common;

public class ServiceBusInvalidObjectException extends RuntimeException{

    public ServiceBusInvalidObjectException(InternalEvent event) {
        super(String.format("ServiceBus does not recognizes Object of type: %s",
                event.getSource().getClass().getCanonicalName()));
    }
}
