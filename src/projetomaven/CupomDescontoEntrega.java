
package projetomaven;

public class CupomDescontoEntrega {
    private String nomeMetodo;
    private double valorDesconto;

    public CupomDescontoEntrega(String nomeMetodo, double valorDesconto){
        if (nomeMetodo == null || nomeMetodo.isBlank()) {
            throw new IllegalArgumentException("Nome do método não pode ser vazio");
        }
        if (valorDesconto < 0) {
            throw new IllegalArgumentException("Valor do desconto não pode ser negativo");
        }
        this.nomeMetodo = nomeMetodo;
        this.valorDesconto = valorDesconto;
    }

    public String getNomeMetodo(){
        return nomeMetodo;
    }
    
    public double getValorDesconto(){
        return valorDesconto;
    }

    @Override
    public String toString(){
        return String.format("Cupom: %s Valor: R$ %.2f", nomeMetodo, valorDesconto);
    }
}
