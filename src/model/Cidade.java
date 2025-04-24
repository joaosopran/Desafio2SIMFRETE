package model;


public class Cidade {
    private String nome;
    private int cepInicio;
    private int cepFim;

    public Cidade(String nome, int cepInicio, int cepFim) {
        this.nome = nome;
        this.cepInicio = cepInicio;
        this.cepFim = cepFim;
    }

    public String getNome() {
        return nome;
    }

    public boolean contemCep(int cep) {
        return cep >= cepInicio && cep <= cepFim;
    }
}
