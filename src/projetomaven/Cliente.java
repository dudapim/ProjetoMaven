
package projetomaven;

public class Cliente {
    private String nome;
    private String tipo;
    private double fidelidade;
    private String logradouro;
    private String bairro;
    private String cidade;

    public Cliente(String nome, String tipo, double fidelidade, String logradouro, String bairro, String cidade){
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe um nome válido");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("Informe um tipo de cliente válido");
        }
        if (bairro == null || bairro.isBlank()) {
            throw new IllegalArgumentException("Informe um bairro válido");
        }
        if (fidelidade < 0) {
            throw new IllegalArgumentException("Fidelidade não pode ser negativa");
        }
        this.nome = nome;
        this.tipo = tipo;
        this.fidelidade = fidelidade;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
    }
    
      public String getNome(){
        return nome;
    }

    public String getTipo(){
        return tipo;
    }

    public double getFidelidade(){
        return fidelidade;
    }

    public String getLogradouro(){
        return logradouro;
    }

    public String getBairro(){
        return bairro;
    }

    public String getCidade(){
        return cidade;
    }
    
    public void setFidelidade(double fidelidade){
        if (fidelidade < 0) {
            throw new IllegalArgumentException("Fidelidade não pode ser negativa");
        }
        this.fidelidade = fidelidade;
    }

    @Override
    public String toString(){
        return String.format("Cliente: %s (%s), %s, %s - %s",
                nome, tipo, logradouro, bairro, cidade);
    }
   
}
