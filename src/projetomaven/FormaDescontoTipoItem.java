
package projetomaven;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class FormaDescontoTipoItem implements IFormaDescontoTaxaEntrega  {
    private Map<String, Double> descontosPorTipoItem;

    public FormaDescontoTipoItem() {
        descontosPorTipoItem = new HashMap<>();
        descontosPorTipoItem.put("Alimentação", 5.00);
        descontosPorTipoItem.put("Educação", 2.00);
        descontosPorTipoItem.put("Lazer", 1.50);
    }

    @Override
    public Optional<CupomDescontoEntrega> calcularDesconto(Pedido pedido) {
        if (!seAplica(pedido)) {
            return Optional.empty();
        }

        double descontoTotal = 0;
        for (Item item : pedido.getItens()) {
            descontoTotal += descontosPorTipoItem.getOrDefault(item.getTipo(), 0.0);
        }

        return Optional.of(new CupomDescontoEntrega("Desconto por Tipo de Item", descontoTotal));
    }
     @Override
    public boolean seAplica(Pedido pedido) {
        for (Item item : pedido.getItens()) {
            if (descontosPorTipoItem.containsKey(item.getTipo())) {
                return true;
            }
        }
        return false;
    }
}
