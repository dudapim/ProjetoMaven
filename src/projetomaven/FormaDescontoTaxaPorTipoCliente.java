
package projetomaven;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FormaDescontoTaxaPorTipoCliente implements IFormaDescontoTaxaEntrega {
    private Map<String, Double> descontosPorTipoCliente;
    private String tipoCliente;

    public FormaDescontoTaxaPorTipoCliente() {
        descontosPorTipoCliente = new HashMap<>();
        descontosPorTipoCliente.put("Ouro", 3.00);
        descontosPorTipoCliente.put("Prata", 2.00);
        descontosPorTipoCliente.put("Bronze", 1.00);
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido) {
        if (!seAplica(pedido)) {
            return Optional.empty();
        }

        double desconto = descontosPorTipoCliente.get(tipoCliente);
        return Optional.of(new CupomDescontoEntrega("Desconto por Tipo de Cliente", desconto));
    }
    
    @Override
    public boolean seAplica(Pedido pedido) {
        tipoCliente = pedido.getCliente().getTipo();
        return descontosPorTipoCliente.containsKey(tipoCliente);
    }
}
