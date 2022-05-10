package br.org.enascimento.assembleiacooperados.write.domain.core;

import br.org.enascimento.assembleiacooperados.common.Consts;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

import static br.org.enascimento.assembleiacooperados.common.Consts.TIPO_TELEFONE_CELULAR;
import static br.org.enascimento.assembleiacooperados.common.Consts.TIPO_TELEFONE_FIXO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Operadora {
    OI(14, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(2)),
    VIVO(15, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(1)),
    TIM(41, TIPO_TELEFONE_CELULAR, BigDecimal.valueOf(3)),
    GVT(25, TIPO_TELEFONE_FIXO, BigDecimal.valueOf(1)),
    EMBRATEL(21, TIPO_TELEFONE_FIXO, BigDecimal.valueOf(2));

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
    public BigDecimal getPreco() {
        return preco;
    }
}