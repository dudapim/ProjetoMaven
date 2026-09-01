package projetomaven;

import java.util.HashMap;
import java.util.Map;

public class RepositorioCupons {
    // Usamos um Map (chave/valor) onde a chave é o código do cupom, 
    // facilitando muito a busca na hora de validar.
    private Map<String, Cupom> cuponsDisponiveis;

    public RepositorioCupons() {
        this.cuponsDisponiveis = new HashMap<>();
    }

    // Atende à RN 3: novos cupons poderão ser incluídos
    public void adicionarCupom(Cupom cupom) {
        if (cupom != null) {
            cuponsDisponiveis.put(cupom.getCodigo(), cupom);
        }
    }

    // Atende à RN 3: cupom existente poderá ser removido
    public void removerCupom(String codigo) {
        cuponsDisponiveis.remove(codigo);
    }

    // Usado na RN 4.1: verificar se o código informado existe
    public Cupom buscarPorCodigo(String codigo) {
        return cuponsDisponiveis.get(codigo);
    }
}