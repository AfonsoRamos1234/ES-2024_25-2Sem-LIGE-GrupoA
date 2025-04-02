package com.projeto.territorio;

import java.util.*;

public class GrafoProprietarios {

    private Map<String, Set<String>> adjacencias;

    public GrafoProprietarios() {
        this.adjacencias = new HashMap<>();
    }

    public void adicionarProprietario(String id) {
        adjacencias.putIfAbsent(id, new HashSet<>());
    }

    public void adicionarAresta(String p1, String p2) {
        if (!p1.equals(p2)) {
            adjacencias.get(p1).add(p2);
            adjacencias.get(p2).add(p1);
        }
    }

    public Set<String> getVizinhos(String idProprietario) {
        return adjacencias.getOrDefault(idProprietario, new HashSet<>());
    }

    public Set<String> getProprietarios() {
        return adjacencias.keySet();
    }

    public int numeroDeArestas() {
        int total = 0;
        for (String p : adjacencias.keySet()) {
            total += adjacencias.get(p).size();
        }
        return total / 2; // porque cada ligação é bidirecional
    }
}
