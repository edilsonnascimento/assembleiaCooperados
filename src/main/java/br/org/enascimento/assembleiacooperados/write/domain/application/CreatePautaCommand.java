package br.org.enascimento.assembleiacooperados.write.domain.application;

import javax.validation.constraints.*;
import java.util.UUID;

public record CreatePautaCommand(

        @NotNull
        UUID uuid,

        @Size(min = 5, max = 100, message = "About Me must be between 5 and 200 characters")
        @NotNull
        String titulo,

        @NotBlank
        String descricao) implements Command
{}
