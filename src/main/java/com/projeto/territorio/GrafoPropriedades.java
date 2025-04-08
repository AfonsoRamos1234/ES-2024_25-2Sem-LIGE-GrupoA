package com.projeto.territorio;

import java.util.*;

/**
 * Representa um grafo de propriedades, onde cada nó é uma propriedade
 * e as arestas representam relações de vizinhança entre elas.
 */
public class GrafoPropriedades {

    private Map<Propriedade, Set<Propriedade>> adjacencias;

    public GrafoPropriedades() {
        this.adjacencias = new HashMap<>();
    }

    /**
     * Adiciona uma propriedade ao grafo, inicializando sua lista de vizinhos.
     *
     * @param p Propriedade a ser adicionada.
     */
    public void adicionarPropriedade(Propriedade p) {
        adjacencias.putIfAbsent(p, new HashSet<Propriedade>());
    }

    /**
     * Cria uma ligação bidirecional entre duas propriedades.
     *
     * @param p1 Primeira propriedade.
     * @param p2 Segunda propriedade.
     */
    public void adicionarAresta(Propriedade p1, Propriedade p2) {
        adjacencias.get(p1).add(p2);
        adjacencias.get(p2).add(p1);
    }

    /**
     * Retorna o conjunto de propriedades vizinhas a uma propriedade fornecida.
     *
     * @param p Propriedade cuja vizinhança será retornada.
     * @return Conjunto de propriedades adjacentes.
     */
    public Set<Propriedade> getAdjacentes(Propriedade p) {
        return adjacencias.getOrDefault(p, new HashSet<Propriedade>());
    }

    /**
     * Retorna todas as propriedades presentes no grafo.
     *
     * @return Conjunto de propriedades.
     */
    public Set<Propriedade> getPropriedades() {
        return adjacencias.keySet();
    }

    /**
     * Retorna o número total de arestas no grafo.
     * Cada ligação entre duas propriedades é contada apenas uma vez.
     *
     * @return Número de arestas.
     */
    public int numeroDeArestas() {
        int total = 0;
        for (Propriedade p : adjacencias.keySet()) {
            total += adjacencias.get(p).size();
        }
        return total / 2; // porque são bidirecionais
    }

    /**
     * Adiciona uma propriedade vizinha à lista de adjacência da propriedade fornecida.
     * Caso a propriedade não exista no grafo, ela será adicionada.
     *
     * @param p1 Propriedade base.
     * @param p2 Propriedade vizinha a ser conectada.
     */
    public void setNgbh(Propriedade p1, Propriedade p2) {
        adjacencias.computeIfAbsent(p1, k -> new HashSet<>()).add(p2);
    }
}
