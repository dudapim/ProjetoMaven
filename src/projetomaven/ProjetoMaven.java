package projetomaven;

import java.time.LocalDateTime;
public class ProjetoMaven {
    public static void main(String[] args) {
        try {
            Cliente cliente = new Cliente("Duda", "Ouro", 10.0, "Rua A", "Centro", "Guaçuí");

            Pedido pedido = new Pedido(LocalDateTime.now(), cliente);
            pedido.adicionarItem(new Item("Comida", 1, 50.0, "Alimentação"));
            pedido.adicionarItem(new Item("Livro", 1, 160.0, "Educação"));

            CalculadoraTaxaDeDescontoService service = new CalculadoraTaxaDeDescontoService();
            service.calcularDesconto(pedido);

            System.out.println(pedido);
            System.out.println("Cupons aplicados:");
            for (CupomDescontoEntrega c : pedido.getCuponsDescontoEntrega()) {
                System.out.println(" - " + c);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
}
