package com.projeto.territorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Classe responsável por sugerir possíveis trocas de terrenos entre diferentes proprietários.
 * Avalia o potencial de troca com base na proximidade e semelhança de área entre as propriedades.
 */
public class Trocas {

    /**
     * Gera uma lista de sugestões de trocas entre propriedades de diferentes proprietários.
     * A troca é sugerida apenas se os proprietários forem diferentes e a diferença de área for aceitável.
     *
     * @param propriedades Conjunto de propriedades disponíveis.
     * @param grafo Grafo de propriedades que define as vizinhanças entre propriedades.
     * @return Lista de trocas sugeridas ordenadas por potencial de sucesso.
     */
    public List<TrocaSugerida> sugerir(Set<Propriedade> propriedades, GrafoPropriedades grafo) {
        List<TrocaSugerida> sugestoes = new ArrayList<>();
        //System.out.println(propriedades);
        for (Propriedade p1 : propriedades) {
            String dono1 = p1.getIdProprietario();
            //System.out.println(dono1);
            for (Propriedade vizinha : grafo.getAdjacentes(p1)) {
                String dono2 = vizinha.getIdProprietario();
                //System.out.println(dono2);
                boolean potencial=trocaTemPotencial(p1, vizinha);
                if (!dono1.equals(dono2) && potencial) {
                    System.out.println("Entrei no if");
                    sugestoes.add(new TrocaSugerida(dono1, p1, dono2, vizinha));
                }
            }
        }

        sugestoes.sort(Comparator.comparingDouble(this::avaliarPotencialTroca).reversed());
        return sugestoes;
    }

    /**
     * Verifica se uma troca entre duas propriedades tem potencial com base na diferença de área.
     *
     * @param a Primeira propriedade.
     * @param b Segunda propriedade.
     * @return true se a diferença de área estiver dentro do limite permitido.
     */
    public boolean trocaTemPotencial(Propriedade a, Propriedade b) {
        //System.out.println("Entrei com potencial");
        double areaDiff = Math.abs(a.getArea() - b.getArea());
        return areaDiff <= 100; // or your own threshold
    }

    /**
     * Avalia a qualidade de uma troca sugerida com base na diferença de área entre as propriedades.
     *
     * @param troca Troca sugerida a ser avaliada.
     * @return Pontuação da troca (quanto mais próximo de 1, melhor).
     */
    public double avaliarPotencialTroca(TrocaSugerida troca) {
        double areaScore = 1.0 / (1 + Math.abs(troca.getProp1().getArea() - troca.getProp2().getArea()));
        return areaScore;
    }
}
