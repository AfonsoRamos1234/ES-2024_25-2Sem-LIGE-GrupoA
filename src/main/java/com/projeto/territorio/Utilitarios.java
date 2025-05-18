package com.projeto.territorio;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe utilitária para cálculos de apoio à decisão sobre propriedades territoriais.
 */
public class Utilitarios {

    // Construtor privado para evitar instanciamento da classe utilitária
    private Utilitarios() {
        throw new UnsupportedOperationException("Classe utilitária, não instanciar.");
    }

    /**
     * PASSO 4 – Calcula a área média de propriedades numa zona administrativa (sem agregação)
     *
     * @param propriedades lista de propriedades
     * @param tipoZona tipo da zona: "freguesia", "concelho", "distrito"
     * @param nomeZona nome da zona administrativa
     * @return área média das propriedades na zona
     */
    public static double areaMediaZona(List<Propriedade> propriedades, String tipoZona, String nomeZona) {
        List<Propriedade> filtradas = propriedades.stream().filter(p -> {
            if (tipoZona.equalsIgnoreCase("freguesia")) {
                return p.getFreguesia().equalsIgnoreCase(nomeZona);
            } else if (tipoZona.equalsIgnoreCase("concelho")) {
                return p.getConcelho().equalsIgnoreCase(nomeZona);
            } else if (tipoZona.equalsIgnoreCase("distrito")) {
                return p.getDistrito().equalsIgnoreCase(nomeZona);
            }
            return false;
        }).collect(Collectors.toList());

        if (filtradas.isEmpty()) return 0.0;

        double totalArea = filtradas.stream().mapToDouble(Propriedade::getArea).sum();
        return totalArea / filtradas.size();
    }

    /**
     * PASSO 5 – Calcula a área média considerando agregações de propriedades contíguas do mesmo proprietário
     *
     * @param propriedades lista de propriedades
     * @param grafo grafo de propriedades (adjacências)
     * @param tipoZona tipo da zona: "freguesia", "concelho", "distrito"
     * @param nomeZona nome da zona
     * @return área média agregada
     */
    public static double areaMediaAgregada(List<Propriedade> propriedades, GrafoPropriedades grafo, String tipoZona, String nomeZona) {
        List<Propriedade> filtradas = propriedades.stream().filter(p -> {
            if (tipoZona.equalsIgnoreCase("freguesia")) {
                return p.getFreguesia().equalsIgnoreCase(nomeZona);
            } else if (tipoZona.equalsIgnoreCase("concelho")) {
                return p.getConcelho().equalsIgnoreCase(nomeZona);
            } else if (tipoZona.equalsIgnoreCase("distrito")) {
                return p.getDistrito().equalsIgnoreCase(nomeZona);
            }
            return false;
        }).collect(Collectors.toList());

        Set<Propriedade> visitadas = new HashSet<>();
        List<Double> areasCombinadas = new ArrayList<>();

        for (Propriedade p : filtradas) {
            if (!visitadas.contains(p)) {
                double areaCombinada = dfsAgregado(p, grafo, visitadas);
                areasCombinadas.add(areaCombinada);
            }
        }

        if (areasCombinadas.isEmpty()) return 0.0;
        return areasCombinadas.stream().mapToDouble(Double::doubleValue).sum() / areasCombinadas.size();
    }

    // Algoritmo DFS para somar áreas de propriedades adjacentes do mesmo dono
    private static double dfsAgregado(Propriedade p, GrafoPropriedades grafo, Set<Propriedade> visitadas) {
        Deque<Propriedade> stack = new ArrayDeque<>();
        stack.push(p);
        visitadas.add(p);
        double total = 0.0;

        while (!stack.isEmpty()) {
            Propriedade atual = stack.pop();
            total += atual.getArea();
            for (Propriedade vizinha : grafo.getAdjacentes(atual)) {
                if (!visitadas.contains(vizinha) && vizinha.getIdProprietario().equals(atual.getIdProprietario())) {
                    visitadas.add(vizinha);
                    stack.push(vizinha);
                }
            }
        }

        return total;
    }
}
