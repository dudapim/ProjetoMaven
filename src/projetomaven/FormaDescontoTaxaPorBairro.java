
package projetomaven;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class FormaDescontoTaxaPorBairro implements IFormaDescontoTaxaEntrega {
    private Map<String, Double> descontosPorBairro;

    public FormaDescontoTaxaPorBairro() {
        descontosPorBairro = new HashMap<>();
        descontosPorBairro.put("Centro", 2.00);
        descontosPorBairro.put("Cidade Maravilhosa", 1.50);
        descontosPorBairro.put("Bela Vista", 3.00);
    }
    
    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido) {
        if (!seAplica(pedido)) {
            return Optional.empty();
        }

        double desconto = descontosPorBairro.get(pedido.getCliente().getBairro());
        return Optional.of(new CupomDescontoEntrega("Desconto por Bairro", desconto));
    }

    @Override
    public boolean seAplica(Pedido pedido) {
        return descontosPorBairro.containsKey(pedido.getCliente().getBairro());
    }

}
