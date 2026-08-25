
package projetomaven;

public class Item {
    private String nome;
    private int quantidade;
    private double valorUnitario;
    private String tipo;

    public Item(String nome, int quantidade, double valorUnitario, String tipo){
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do item não pode ser vazio");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Informe um tipo de item válido");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo");
        }
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }
    
    public double getValorTotal() {
        return this.quantidade * this.valorUnitario;
    }

    public String getNome(){
        return nome;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public double getValorUnitario(){
        return valorUnitario;
    }

    public String getTipo(){
        return tipo;
    }
    
    
    @Override
    public String toString() {
        return String.format("%dx %s (%s) - R$ %.2f", quantidade, nome, tipo, getValorTotal());
    }
}
