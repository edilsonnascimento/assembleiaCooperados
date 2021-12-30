package br.org.enascimento.assembleiacooperados.common;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class DomainController {

    @Autowired
    protected ServiceBus serviceBus;
}
