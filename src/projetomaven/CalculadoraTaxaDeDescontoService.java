
package projetomaven;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CalculadoraTaxaDeDescontoService {
    private List<IFormaDescontoTaxaEntrega> metodosDesconto;

    public CalculadoraTaxaDeDescontoService() {
        metodosDesconto = new ArrayList<>();
        metodosDesconto.add(new FormaDescontoTipoItem());
        metodosDesconto.add(new FormaDescontoTaxaPorBairro());
        metodosDesconto.add(new FormaDescontoValorPedido(200.00));
        metodosDesconto.add(new FormaDescontoTaxaPorTipoCliente());
    }
    
    
    public void calcularDesconto(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Informe um pedido válido");
        }

        for (IFormaDescontoTaxaEntrega metodo : metodosDesconto) {
            Optional<CupomDescontoEntrega> resultado = metodo.calcularDesconto(pedido);

            if (resultado.isPresent()) {
                pedido.aplicarDesconto(resultado.get());
            }
        }
    }
}
