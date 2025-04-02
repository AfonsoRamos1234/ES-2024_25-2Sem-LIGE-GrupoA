package com.projeto.territorio;

import java.util.*;

public class GrafoPropriedades {

    private Map<Propriedade, Set<Propriedade>> adjacencias;

    public GrafoPropriedades() {
        this.adjacencias = new HashMap<>();
    }

    public void adicionarPropriedade(Propriedade p) {
        adjacencias.putIfAbsent(p, new HashSet<>());
    }

    public void adicionarAresta(Propriedade p1, Propriedade p2) {
        adjacencias.get(p1).add(p2);
        adjacencias.get(p2).add(p1);
    }

    public Set<Propriedade> getAdjacentes(Propriedade p) {
        return adjacencias.getOrDefault(p, new HashSet<>());
    }

    public Set<Propriedade> getPropriedades() {
        return adjacencias.keySet();
    }

    public int numeroDeArestas() {
        int total = 0;
        for (Propriedade p : adjacencias.keySet()) {
            total += adjacencias.get(p).size();
        }
        return total / 2; // porque são bidirecionais
    }
}
