package br.org.enascimento.assembleiacooperados.write.domain.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Operadora {
    OI(14, "Celular", BigDecimal.valueOf(2)),
    VIVO(15, "Celular", BigDecimal.valueOf(1)),
    TIM(41, "Celular", BigDecimal.valueOf(3)),
    GVT(25, "Fixo", BigDecimal.valueOf(1)),
    EMBRATEL(21, "Fixo", BigDecimal.valueOf(2));

    private Integer codigo;
    private String categoria;
    private BigDecimal preco;

    Operadora(Integer codigo, String categoria, BigDecimal preco) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.preco = preco;
    }
    public String getNome(){
        return this.toString();
    }
    public Integer getCodigo() {
        return codigo;
    }
    public String getCategoria() {
        return categoria;
    }
}