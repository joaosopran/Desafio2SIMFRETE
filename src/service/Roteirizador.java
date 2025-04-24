package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import model.Cidade;
import model.Conexao;
import model.Rota;

public class Roteirizador {
    private Map<String, Cidade> cidades = new HashMap<>();
    private Map<String, List<Conexao>> rotas = new HashMap<>();

    public void adicionarCidade(String nome, int cepInicio, int cepFim) {
        Cidade cidade = new Cidade(nome, cepInicio, cepFim);
        cidades.put(nome, cidade);
        rotas.put(nome, new ArrayList<>());
    }

    public void adicionarConexao(String origem, String destino, double custo) {
        rotas.get(origem).add(new Conexao(destino, custo));
        rotas.get(destino).add(new Conexao(origem, custo));
    }

    public String buscarCidadePorCep(int cep) {
        for (Cidade cidade : cidades.values()) {
            if (cidade.contemCep(cep)) return cidade.getNome();
        }
        return null;
    }

    public List<String> calcularRotaMaisBarata(String cepOrigem, String cepDestino) {
        String cidadeOrigem = buscarCidadePorCep(Integer.parseInt(cepOrigem));
        String cidadeDestino = buscarCidadePorCep(Integer.parseInt(cepDestino));

        if (cidadeOrigem == null || cidadeDestino == null) return null;

        Map<String, Double> custos = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        PriorityQueue<Rota> fila = new PriorityQueue<>();

        for (String cidade : rotas.keySet()) {
            custos.put(cidade, Double.MAX_VALUE);
        }

        custos.put(cidadeOrigem, 0.0);
        fila.add(new Rota(cidadeOrigem, 0.0));

        while (!fila.isEmpty()) {
            Rota atual = fila.poll();
            if (atual.cidade.equals(cidadeDestino)) break;

            for (Conexao conexao : rotas.get(atual.cidade)) {
                double novoCusto = custos.get(atual.cidade) + conexao.custo;
                if (novoCusto < custos.get(conexao.cidadeDestino)) {
                    custos.put(conexao.cidadeDestino, novoCusto);
                    anteriores.put(conexao.cidadeDestino, atual.cidade);
                    fila.add(new Rota(conexao.cidadeDestino, novoCusto));
                }
            }
        }

        if (custos.get(cidadeDestino) == Double.MAX_VALUE) return null;

        List<String> caminho = new ArrayList<>();
        for (String at = cidadeDestino; at != null; at = anteriores.get(at)) {
            caminho.add(at);
        }
        Collections.reverse(caminho);
        caminho.add(String.format("CUSTO: %.2f", custos.get(cidadeDestino)));

        return caminho;
    }
}
