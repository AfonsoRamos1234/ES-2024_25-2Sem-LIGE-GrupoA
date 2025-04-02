package com.projeto.territorio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public static List<Propriedade> carregarPropriedades(String caminhoCSV) {
        List<Propriedade> propriedades = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoCSV))) {
            String linha;
            br.readLine(); // Ignorar cabeçalho
            int contador = 1;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");

                try {
                    double area = Double.parseDouble(campos[4]);
                    String id = "P" + contador;
                    String idProprietario = campos[6];
                    String freguesia = campos[7];
                    String concelho = campos[8];
                    String distrito = campos[9];

                    propriedades.add(new Propriedade(id, area, idProprietario, 0.0, 0.0,
                            freguesia, concelho, distrito));

                    contador++;
                } catch (Exception e) {
                    System.out.println("Erro ao processar linha: " + linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propriedades;
    }
}

