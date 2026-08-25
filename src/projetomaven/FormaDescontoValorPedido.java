
package projetomaven;
import java.util.Optional;


public class FormaDescontoValorPedido implements IFormaDescontoTaxaEntrega {
    private static final double VALOR_DESCONTO = 5.00;
    private double limiteValorPedido;

    public FormaDescontoValorPedido(double limiteValorPedido) {
        this.limiteValorPedido = limiteValorPedido;
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido) {
        if (!seAplica(pedido)) {
            return Optional.empty();
        }

        return Optional.of(new CupomDescontoEntrega("Desconto por Valor do Pedido", VALOR_DESCONTO));
    }
    
    @Override
    public boolean seAplica(Pedido pedido) {
        return pedido.getValorPedido() > limiteValorPedido;
    }
}
