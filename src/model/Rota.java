package model;

public class Rota implements Comparable<Rota> {
    public String cidade;
    public double custoTotal;

    public Rota(String cidade, double custoTotal) {
        this.cidade = cidade;
        this.custoTotal = custoTotal;
    }

    @Override
    public int compareTo(Rota outra) {
        return Double.compare(this.custoTotal, outra.custoTotal);
    }
}
