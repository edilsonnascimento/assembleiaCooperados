package br.org.enascimento.assembleiacooperados.write.domain.core;

import java.math.BigDecimal;

public enum Operadora {
    OI(14, "Celular", BigDecimal.valueOf(2)),
    VIVO(15, "Celular", BigDecimal.valueOf(1)),
    TIM(41, "Celular", BigDecimal.valueOf(3)),
    GVT(25, "Fixo", BigDecimal.valueOf(1)),
    EMBRATEL(21, "Fixo", BigDecimal.valueOf(2));

    Operadora(Integer codigo, String categoria, BigDecimal preco) {
    }
}
