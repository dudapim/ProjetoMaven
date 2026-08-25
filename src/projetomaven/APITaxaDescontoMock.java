
package projetomaven;

public class APITaxaDescontoMock {
    private static double TAXA_ENTREGA = 10.00;

    public static double getTaxaEntregaPadrao() {
        return TAXA_ENTREGA;
    }

    public static void setTaxaEntregaPadrao(double novaTaxaEntrega) {
        TAXA_ENTREGA = novaTaxaEntrega;
    }
}
