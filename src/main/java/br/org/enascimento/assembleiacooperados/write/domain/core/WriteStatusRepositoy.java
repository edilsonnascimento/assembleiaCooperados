package br.org.enascimento.assembleiacooperados.write.domain.core;

public interface WriteStatusRepositoy {
    boolean create(Status status);
    void update(Status status);
}
