
package projetomaven;
import java.util.Optional;


public interface IFormaDescontoTaxaEntrega {
    Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido);
    boolean seAplica(Pedido pedido);
}
