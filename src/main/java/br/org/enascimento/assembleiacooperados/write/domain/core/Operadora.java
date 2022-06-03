package br.org.enascimento.assembleiacooperados.write.domain.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

import static br.org.enascimento.assembleiacooperados.common.Consts.TIPO_TELEFONE_CELULAR;
import static br.org.enascimento.assembleiacooperados.common.Consts.TIPO_TELEFONE_FIXO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Operadora {
    OI(14, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(2), BigDecimal.ZERO),
    VIVO(15, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(4), BigDecimal.ZERO),
    TIM(41, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(3), BigDecimal.ZERO),
    GVT(25, TIPO_TELEFONE_FIXO, BigDecimal.valueOf(1), BigDecimal.ZERO),
    EMBRATEL(21, TIPO_TELEFONE_FIXO, BigDecimal.valueOf(5), BigDecimal.ZERO);

    private Integer codigo;
    private String categoria;
    private BigDecimal preco;
    private BigDecimal precoImposto;

    Operadora(Integer codigo, String categoria, BigDecimal preco, BigDecimal precoImposto) {
        this.codigo = codigo;
        this.categoria = categoria;
        this.preco = preco;
        this.precoImposto = precoImposto;
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
    public BigDecimal getPreco() {
        return preco;
    }
    public BigDecimal getPrecoImposto() {
        return precoImposto;
    }
}