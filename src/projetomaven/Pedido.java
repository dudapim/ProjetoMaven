
package projetomaven;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private double taxaEntrega = APITaxaDescontoMock.getTaxaEntregaPadrao();
    private LocalDateTime data;
    private Cliente cliente;
    private List<Item> itens;
    private List<CupomDescontoEntrega> cuponsDescontoEntrega;

    public Pedido(LocalDateTime data, Cliente cliente){
        if (data == null) {
            throw new IllegalArgumentException("Informe uma data válida");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Informe um cliente válido");
        }
        this.data = data;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.cuponsDescontoEntrega = new ArrayList<>();
    }
    
    public void adicionarItem(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        this.itens.add(item);
    }

    public double getValorPedido() {
        double total = 0;
        for (Item item : itens) {
            total += item.getValorTotal();
        }
        return total;
    }
    
     public void aplicarDesconto(CupomDescontoEntrega cupom) {
        if (cupom == null) {
            throw new IllegalArgumentException("Cupom não pode ser nulo");
        }
        if (taxaEntrega <= 0) {
            return;
        }

        double valorDesconto = cupom.getValorDesconto();
        boolean foiParcial = false;

        if (valorDesconto > taxaEntrega) {
            valorDesconto = taxaEntrega;
            foiParcial = true;
        }

        taxaEntrega -= valorDesconto;

        CupomDescontoEntrega cupomAplicado = foiParcial
                ? new CupomDescontoEntrega(cupom.getNomeMetodo() + " (parcial)", valorDesconto)
                : new CupomDescontoEntrega(cupom.getNomeMetodo(), valorDesconto);

        this.cuponsDescontoEntrega.add(cupomAplicado);
    }   
        
    public double getDescontoConcedido() {
        double totalDesconto = 0;
        for (CupomDescontoEntrega cupom : cuponsDescontoEntrega) {
            totalDesconto += cupom.getValorDesconto();
        }
        return totalDesconto;
    }

    public LocalDateTime getData(){
        return data;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public List<Item> getItens(){
        return List.copyOf(itens);
    }
   
    
    public List<CupomDescontoEntrega> getCuponsDescontoEntrega(){
        return List.copyOf(cuponsDescontoEntrega);
    }

    public double getTaxaEntrega(){
        return taxaEntrega;
    }

    @Override
    public String toString() {
        return String.format("Pedido para %s | Itens: %d | Total Produtos: R$ %.2f | Taxa Entrega: R$ %.2f | Desconto Concedido: R$ %.2f",
                cliente.getNome(), itens.size(), getValorPedido(), taxaEntrega, getDescontoConcedido());
    }
}
