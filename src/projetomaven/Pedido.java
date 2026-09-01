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

    // NOVOS ATRIBUTOS DA CR1 (Armazenamento em memória - RNF 1.1)
    private String codigoCupomAplicado;
    private double percentualDescontoAplicado;

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
        
        // Inicializa sem nenhum cupom global aplicado (RN 9)
        this.codigoCupomAplicado = null;
        this.percentualDescontoAplicado = 0.0;
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
        // RN 7 - Se já possuir cupom global, não permite outro tipo de desconto.
        if (this.codigoCupomAplicado != null) {
            throw new IllegalArgumentException("Rejeitado: O pedido já possui um cupom global aplicado. Não é permitido adicionar outro tipo de desconto.");
        }

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

    // --- NOVA FUNCIONALIDADE: Aplicação de Cupom da CR1 ---
    public void aplicarCupom(String codigoInformado, RepositorioCupons repositorio, LocalDateTime dataHoraAtual) {
        // 4.1. Verifica se o código informado existe na relação de cupons disponíveis
        Cupom cupom = repositorio.buscarPorCodigo(codigoInformado);
        if (cupom == null) {
            throw new IllegalArgumentException("Rejeitado: Cupom informado não existe."); // RN 8
        }

        // 4.2. Verifica se a data/hora estão dentro do intervalo (RNF 4.1 e 4.2)
        boolean antesDoInicio = dataHoraAtual.isBefore(cupom.getInicioValidade());
        boolean depoisDoFim = dataHoraAtual.isAfter(cupom.getFimValidade());
        
        if (antesDoInicio || depoisDoFim) {
            throw new IllegalArgumentException("Rejeitado: Cupom fora da janela de validade."); // RN 8
        }

        // 4.3 e 4.4. Verifica se o pedido já tem cupom e se o novo é ESTRITAMENTE MAIOR
        if (this.codigoCupomAplicado != null) {
            if (cupom.getPercentualDesconto() <= this.percentualDescontoAplicado) {
                throw new IllegalArgumentException("Rejeitado: O novo cupom deve ter um percentual estritamente maior que o já aplicado."); // RN 4.4, RN 8, RNF 3.2
            }
        }

        // 5. Aceite do cupom: Grava o código e o percentual no pedido (memória)
        this.codigoCupomAplicado = cupom.getCodigo();
        this.percentualDescontoAplicado = cupom.getPercentualDesconto();
    }

    // RN 6 e RNF 2.1: Cálculo sobre o total (produtos + taxa de entrega) integrado
    public double getValorFinal() {
        double valorBase = getValorPedido() + this.taxaEntrega;
        
        if (this.codigoCupomAplicado != null) {
            double valorDescontoGlobal = valorBase * (this.percentualDescontoAplicado / 100.0);
            return valorBase - valorDescontoGlobal;
        }
        
        return valorBase; // Se não tem cupom, fica sem desconto global (RN 9)
    }

    // Getters para os novos atributos
    public String getCodigoCupomAplicado() {
        return codigoCupomAplicado;
    }

    public double getPercentualDescontoAplicado() {
        return percentualDescontoAplicado;
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
        String texto = String.format("Pedido para %s | Itens: %d | Total Produtos: R$ %.2f | Taxa Entrega: R$ %.2f",
                cliente.getNome(), itens.size(), getValorPedido(), taxaEntrega);
        
        if (codigoCupomAplicado != null) {
            texto += String.format(" | Cupom: %s (%.0f%%) | VALOR FINAL: R$ %.2f", 
                    codigoCupomAplicado, percentualDescontoAplicado, getValorFinal());
        } else {
            texto += String.format(" | VALOR FINAL: R$ %.2f", getValorFinal());
        }
        
        return texto;
    }
}