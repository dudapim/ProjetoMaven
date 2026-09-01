package projetomaven;

import java.time.LocalDateTime;
import java.time.Month;

public class ProjetoMaven {
    public static void main(String[] args) {
        
        System.out.println("=== CONFIGURANDO O SISTEMA ===");
        
        // 1. Criando o Repositório de Cupons
        RepositorioCupons repositorio = new RepositorioCupons();
        
        // 2. Adicionando os cupons da CR1 no repositório
        repositorio.adicionarCupom(new Cupom("DESC10", 10.0, 
                LocalDateTime.of(2026, Month.SEPTEMBER, 25, 0, 0), 
                LocalDateTime.of(2026, Month.SEPTEMBER, 27, 23, 59)));
        
        repositorio.adicionarCupom(new Cupom("DESC20", 20.0, 
                LocalDateTime.of(2026, Month.OCTOBER, 1, 0, 0), 
                LocalDateTime.of(2026, Month.OCTOBER, 5, 23, 59)));
        
        repositorio.adicionarCupom(new Cupom("BLACK50", 50.0, 
                LocalDateTime.of(2026, Month.SEPTEMBER, 28, 0, 0), 
                LocalDateTime.of(2026, Month.SEPTEMBER, 28, 23, 59)));
                
        // 3. Configurando o Pedido
        Cliente cliente = new Cliente("Duda", "Ouro", 10.0, "Rua A", "Centro", "Guaçuí");
        Pedido pedido = new Pedido(LocalDateTime.now(), cliente);
        pedido.adicionarItem(new Item("Comida", 1, 50.0, "Alimentação"));
        pedido.adicionarItem(new Item("Livro", 1, 150.0, "Educação"));
        // Total produtos = 200.0 | Taxa Entrega = 10.0 | Total = 210.0
        
        System.out.println("\n--- ESTADO INICIAL DO PEDIDO ---");
        System.out.println(pedido);

        System.out.println("\n=== INICIANDO BATERIA DE TESTES (CR1) ===");

        // TESTE 1: Tentar aplicar cupom ANTES da validade (RN 4.2 e RN 8)
        System.out.println("\n[Teste 1] Tentando aplicar DESC10 no dia 24/09 (Fora da validade):");
        try {
            LocalDateTime tentativa1 = LocalDateTime.of(2026, Month.SEPTEMBER, 24, 15, 0);
            pedido.aplicarCupom("DESC10", repositorio, tentativa1);
        } catch (IllegalArgumentException e) {
            System.out.println(" -> SUCESSO DO SISTEMA: " + e.getMessage());
        }

        // TESTE 2: Tentar aplicar cupom DENTRO da validade (RN 4 e RN 5)
        System.out.println("\n[Teste 2] Tentando aplicar DESC10 no dia 26/09 (Dentro da validade):");
        try {
            LocalDateTime tentativa2 = LocalDateTime.of(2026, Month.SEPTEMBER, 26, 14, 0);
            pedido.aplicarCupom("DESC10", repositorio, tentativa2);
            System.out.println(" -> SUCESSO DO SISTEMA: Cupom aplicado! Agora o pedido tem " + pedido.getPercentualDescontoAplicado() + "% de desconto.");
        } catch (IllegalArgumentException e) {
            System.out.println(" -> ERRO: " + e.getMessage());
        }

        // TESTE 3: Tentar aplicar outro cupom MENOR ou IGUAL, simulando que estivéssemos num dia válido para ele
        System.out.println("\n[Teste 3] Tentando substituir por um cupom de menor ou igual valor (Ex: cupom de 5%):");
        repositorio.adicionarCupom(new Cupom("DESC05", 5.0, LocalDateTime.of(2026, Month.SEPTEMBER, 1, 0, 0), LocalDateTime.of(2026, Month.OCTOBER, 30, 23, 59)));
        try {
            LocalDateTime tentativa3 = LocalDateTime.of(2026, Month.SEPTEMBER, 26, 15, 0);
            pedido.aplicarCupom("DESC05", repositorio, tentativa3);
        } catch (IllegalArgumentException e) {
            System.out.println(" -> SUCESSO DO SISTEMA: " + e.getMessage());
        }

        // TESTE 4: Substituir por um cupom MAIOR (RN 4.4, RN 5, RN 7)
        System.out.println("\n[Teste 4] Tentando substituir DESC10 pelo BLACK50 no dia 28/09 (Maior percentual):");
        try {
            LocalDateTime tentativa4 = LocalDateTime.of(2026, Month.SEPTEMBER, 28, 10, 0);
            pedido.aplicarCupom("BLACK50", repositorio, tentativa4);
            System.out.println(" -> SUCESSO DO SISTEMA: Cupom substituído! Novo desconto: " + pedido.getPercentualDescontoAplicado() + "%");
        } catch (IllegalArgumentException e) {
            System.out.println(" -> ERRO: " + e.getMessage());
        }

        System.out.println("\n=== ESTADO FINAL DO PEDIDO ===");
        // Valor total (200) + Taxa (10) = 210. Com 50% de desconto, deve ser 105.00
        System.out.println(pedido);
    }
}