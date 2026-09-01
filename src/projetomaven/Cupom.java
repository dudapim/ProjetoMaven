package projetomaven;

import java.time.LocalDateTime;

public class Cupom {
    private String codigo;
    private double percentualDesconto;
    private LocalDateTime inicioValidade;
    private LocalDateTime fimValidade;

    public Cupom(String codigo, double percentualDesconto, LocalDateTime inicioValidade, LocalDateTime fimValidade) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código do cupom não pode ser vazio");
        }
        if (percentualDesconto <= 0) {
            throw new IllegalArgumentException("Percentual de desconto deve ser maior que zero");
        }
        if (inicioValidade == null || fimValidade == null) {
            throw new IllegalArgumentException("As datas de validade não podem ser nulas");
        }
        if (inicioValidade.isAfter(fimValidade)) {
            throw new IllegalArgumentException("A data de início não pode ser depois da data de fim");
        }
        
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.inicioValidade = inicioValidade;
        this.fimValidade = fimValidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public LocalDateTime getInicioValidade() {
        return inicioValidade;
    }

    public LocalDateTime getFimValidade() {
        return fimValidade;
    }
}