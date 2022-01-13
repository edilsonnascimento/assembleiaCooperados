package br.org.enascimento.assembleiacooperados.write.adapter.in.controllers;

import br.org.enascimento.assembleiacooperados.common.ServiceBus;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class WriteDomainController {

    @Autowired
    protected ServiceBus serviceBus;
}
