package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import service.Roteirizador;

public class Main {
    public static void main(String[] args) {
        Roteirizador roteirizador = new Roteirizador();
        String caminhoArquivo = "C:\\Users\\João e Bruna\\eclipse-workspace\\Desafio2SIMFRETE\\CEP2.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean lendoRotas = false, 
            		lendoConsulta = false;
            String cepOrigem = "", 
            		cepDestino = "";

            while ((linha = br.readLine()) != null) {
                if (linha.equals("--")) {
                    if (!lendoRotas) lendoRotas = true;
                    else lendoConsulta = true;
                    continue;
                }

                if (!lendoRotas) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 3) {
                        roteirizador.adicionarCidade(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
                    }
                    
                } else if (!lendoConsulta) {
                    String[] partes = linha.split(",");
                    if (partes.length >= 3) {
                        roteirizador.adicionarConexao(partes[0], partes[1], Double.parseDouble(partes[2]));
                    } else {
                        System.err.println("Erro: Formato incorreto na linha de conexão: " + linha);
                        // Trate o erro
                    }
                } else {
                    String[] ceps = linha.split(",");
                    if (ceps.length >= 2) {
                        cepOrigem = ceps[0];
                        cepDestino = ceps[1];
                    } else {
                        System.err.println("Erro: Formato incorreto na linha de CEPs: " + linha);
                        // Trate o erro
                    }
                }
            }

            List<String> rota = roteirizador.calcularRotaMaisBarata(cepOrigem, cepDestino);
            if (rota == null) {
                System.out.println("Não foi possível encontrar uma rota.");
            } else {
                for (String cidade : rota) {
                    System.out.println(cidade);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
