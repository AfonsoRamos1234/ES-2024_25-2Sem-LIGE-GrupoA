package com.projeto.territorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Classe responsável por sugerir possíveis trocas de terrenos entre diferentes proprietários.
 * Avalia o potencial de troca com base em semelhança de área e proximidade geográfica.
 */
public class Trocas {

    private static final double MAX_DIFERENCA_AREA = 50.0; // metros quadrados
    private static final double MAX_DISTANCIA = 100.0;     // metros (ajustável)

    /**
     * Gera uma lista de sugestões de trocas entre propriedades de diferentes proprietários.
     *
     * @param propriedades Conjunto de propriedades disponíveis.
     * @param grafo Grafo de propriedades com adjacência.
     * @return Lista de trocas sugeridas ordenadas por potencial de sucesso.
     */
    public List<TrocaSugerida> sugerir(Set<Propriedade> propriedades, GrafoPropriedades grafo) {
        List<TrocaSugerida> sugestoes = new ArrayList<>();

        for (Propriedade p1 : propriedades) {
            for (Propriedade p2 : grafo.getAdjacentes(p1)) {
                if (trocaTemPotencial(p1, p2)) {
                    sugestoes.add(new TrocaSugerida(p1, p2));
                }
            }
        }

        sugestoes.sort(Comparator
                .comparingDouble(TrocaSugerida::getDiferencaArea)
                .thenComparingDouble(TrocaSugerida::getDistancia));

        return sugestoes;
    }

    /**
     * Verifica se uma troca entre duas propriedades tem potencial de ser viável.
     * @param p1 Primeira propriedade
     * @param p2 Segunda propriedade
     * @return true se a troca for razoável
     */
    private boolean trocaTemPotencial(Propriedade p1, Propriedade p2) {
        if (p1.getIdProprietario().equals(p2.getIdProprietario())) {
            return false;
        }

        double diferencaArea = Math.abs(p1.getArea() - p2.getArea());
        if (diferencaArea > MAX_DIFERENCA_AREA) {
            return false;
        }

        double distancia = calcularDistancia(p1, p2);
        return distancia <= MAX_DISTANCIA;
    }

    /**
     * Calcula a distância euclidiana entre duas propriedades com base em coordenadas x e y.
     */
    private double calcularDistancia(Propriedade p1, Propriedade p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
