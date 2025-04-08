package com.projeto.territorio;

import java.util.*;

/**
 * Classe que representa um grafo de conexões entre proprietários.
 * Cada proprietário é representado como um nó, e uma aresta entre dois nós
 * indica que esses proprietários possuem propriedades adjacentes.
 */
public class GrafoProprietarios {

    private Map<String, Set<String>> adjacencias;

    public GrafoProprietarios() {
        this.adjacencias = new HashMap<>();
    }

    /**
     * Adiciona um proprietário ao grafo, se ainda não estiver presente.
     *
     * @param id ID do proprietário.
     */
    public void adicionarProprietario(String id) {
        adjacencias.putIfAbsent(id, new HashSet<>());
    }

    /**
     * Cria uma ligação bidirecional entre dois proprietários.
     * Essa ligação indica que eles possuem propriedades vizinhas.
     *
     * @param p1 ID do primeiro proprietário.
     * @param p2 ID do segundo proprietário.
     */
    public void adicionarAresta(String p1, String p2) {
        if (!p1.equals(p2)) {
            adjacencias.get(p1).add(p2);
            adjacencias.get(p2).add(p1);
        }
    }

    /**
     * Retorna o conjunto de IDs dos proprietários vizinhos ao proprietário fornecido.
     *
     * @param idProprietario ID do proprietário.
     * @return Conjunto de IDs dos vizinhos.
     */
    public Set<String> getVizinhos(String idProprietario) {
        return adjacencias.getOrDefault(idProprietario, new HashSet<>());
    }

    /**
     * Retorna o conjunto de todos os IDs de proprietários presentes no grafo.
     *
     * @return Conjunto de IDs de proprietários.
     */
    public Set<String> getProprietarios() {
        return adjacencias.keySet();
    }

    /**
     * Calcula o número total de arestas no grafo.
     * Como as ligações são bidirecionais, cada ligação é contada uma vez.
     *
     * @return Número de arestas no grafo.
     */
    public int numeroDeArestas() {
        int total = 0;
        for (String p : adjacencias.keySet()) {
            total += adjacencias.get(p).size();
        }
        return total / 2; // porque cada ligação é bidirecional
    }
}
