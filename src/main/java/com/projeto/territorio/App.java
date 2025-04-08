package com.projeto.territorio;

import java.util.*;

/**
 * Classe principal responsável por carregar dados, construir os grafos e apresentar o menu interativo.
 */
public class App {
    /**
     * Método principal que inicia o programa.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        String caminho = System.getProperty("user.dir") + "/data/Madeira-Moodle-1.1.csv";
        List<Propriedade> propriedades = CsvLoader.carregarPropriedades(caminho);

        // GRAFO DE PROPRIEDADES
        GrafoPropriedades grafo = new GrafoPropriedades();
        for (Propriedade p : propriedades) {
            grafo.adicionarPropriedade(p);
        }

        Map<String, List<Propriedade>> porConcelho = new HashMap<>();

        for (Propriedade p : propriedades) {
            grafo.adicionarPropriedade(p);
            porConcelho.computeIfAbsent(p.getConcelho(), k -> new ArrayList<>()).add(p);
        }

        // Connect small groups only
        for (List<Propriedade> grupo : porConcelho.values()) {
            for (int i = 0; i < grupo.size(); i++) {
                Propriedade p1 = grupo.get(i);
                for (int j = i + 1; j < Math.min(i + 6, grupo.size()); j++) {
                    Propriedade p2 = grupo.get(j);
                    if (!p1.getIdProprietario().equals(p2.getIdProprietario())) {
                        grafo.setNgbh(p1, p2);
                        grafo.setNgbh(p2, p1);
                    }
                }
            }
        }

        // GRAFO DE PROPRIETÁRIOS
        GrafoProprietarios grafoProprietarios = new GrafoProprietarios();
        for (Propriedade p : propriedades) {
            grafoProprietarios.adicionarProprietario(p.getIdProprietario());
        }
        for (Propriedade p : grafo.getPropriedades()) {
            for (Propriedade vizinha : grafo.getAdjacentes(p)) {
                String dono1 = p.getIdProprietario();
                String dono2 = vizinha.getIdProprietario();
                if (!dono1.equals(dono2)) {
                    grafoProprietarios.adicionarAresta(dono1, dono2);
                }
            }
        }

        System.out.println("Grafo de propriedades criado com " + grafo.getPropriedades().size() + " propriedades.");
        System.out.println("Número de arestas: " + grafo.numeroDeArestas());
        System.out.println("Grafo de proprietários criado com " + grafoProprietarios.getProprietarios().size() + " proprietários.");
        System.out.println("Número de ligações (arestas): " + grafoProprietarios.numeroDeArestas());

        // MENU INTERATIVO
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Estatísticas por localidade");
            System.out.println("2. Estatísticas por proprietário");
            System.out.println("3. Identificar blocos adjacentes");
            System.out.println("4. Sugerir trocas de terrenos");
            System.out.println("5. Proprietários com terrenos em vários concelhos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  // limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Tipo (freguesia/concelho/distrito): ");
                    String tipo = scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    estatisticasPorLocal(propriedades, tipo, nome);
                    break;
                case 2:
                    System.out.print("ID do proprietário: ");
                    String id = scanner.nextLine();
                    estatisticasPorProprietario(propriedades, id);
                    break;
                case 3:
                    identificarBlocosAdjacentes(grafo);
                    break;
                case 4:
                    sugerirTrocas(grafo);
                    break;
                case 5:
                    proprietariosMulticoncelho(propriedades);
                    break;
                case 0:
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    /**
     * Exibe estatísticas sobre propriedades com base em localidade (freguesia, concelho ou distrito).
     *
     * @param propriedades Lista de propriedades disponíveis.
     * @param tipo Tipo de localidade (freguesia/concelho/distrito).
     * @param nome Nome da localidade.
     */
    public static void estatisticasPorLocal(List<Propriedade> propriedades, String tipo, String nome) {
        List<Propriedade> filtradas = new ArrayList<>();

        for (Propriedade p : propriedades) {
            if (tipo.equalsIgnoreCase("freguesia") && p.getFreguesia().equalsIgnoreCase(nome)) {
                filtradas.add(p);
            } else if (tipo.equalsIgnoreCase("concelho") && p.getConcelho().equalsIgnoreCase(nome)) {
                filtradas.add(p);
            } else if (tipo.equalsIgnoreCase("distrito") && p.getDistrito().equalsIgnoreCase(nome)) {
                filtradas.add(p);
            }
        }

        int numPropriedades = filtradas.size();
        double areaTotal = filtradas.stream().mapToDouble(Propriedade::getArea).sum();
        double areaMedia = numPropriedades > 0 ? areaTotal / numPropriedades : 0.0;
        long numProprietariosUnicos = filtradas.stream().map(Propriedade::getIdProprietario).distinct().count();

        System.out.println("\n Estatísticas para " + tipo + ": " + nome);
        System.out.println("Número de propriedades: " + numPropriedades);
        System.out.println("Número de proprietários: " + numProprietariosUnicos);
        System.out.println("Área total: " + areaTotal);
        System.out.println("Área média: " + areaMedia);
    }

    /**
     * Exibe estatísticas sobre as propriedades de um proprietário específico.
     *
     * @param propriedades Lista de propriedades disponíveis.
     * @param idProprietario ID do proprietário.
     */
    public static void estatisticasPorProprietario(List<Propriedade> propriedades, String idProprietario) {
        List<Propriedade> doProprietario = new ArrayList<>();

        for (Propriedade p : propriedades) {
            if (p.getIdProprietario().equals(idProprietario)) {
                doProprietario.add(p);
            }
        }

        int numTerrenos = doProprietario.size();
        double areaTotal = doProprietario.stream().mapToDouble(Propriedade::getArea).sum();

        System.out.println("\n Estatísticas do proprietário " + idProprietario);
        System.out.println("Número de terrenos: " + numTerrenos);
        System.out.println("Área total: " + areaTotal);
    }

    /**
     * Identifica blocos de propriedades adjacentes pertencentes ao mesmo proprietário.
     *
     * @param grafo Grafo de propriedades com as adjacências já definidas.
     */
    public static void identificarBlocosAdjacentes(GrafoPropriedades grafo) {
        Set<Propriedade> visitadas = new HashSet<>();
        Map<String, Integer> blocosPorProprietario = new HashMap<>();

        for (Propriedade p : grafo.getPropriedades()) {
            if (!visitadas.contains(p)) {
                Set<Propriedade> componente = new HashSet<>();
                Queue<Propriedade> fila = new LinkedList<>();
                fila.add(p);

                while (!fila.isEmpty()) {
                    Propriedade atual = fila.poll();
                    if (!visitadas.contains(atual)) {
                        visitadas.add(atual);
                        componente.add(atual);
                        for (Propriedade vizinha : grafo.getAdjacentes(atual)) {
                            if (!visitadas.contains(vizinha)) {
                                fila.add(vizinha);
                            }
                        }
                    }
                }

                String dono = p.getIdProprietario();
                boolean todasIguais = componente.stream()
                        .allMatch(prop -> prop.getIdProprietario().equals(dono));

                if (todasIguais) {
                    blocosPorProprietario.put(dono, blocosPorProprietario.getOrDefault(dono, 0) + 1);
                }
            }
        }

        System.out.println("\n Blocos adjacentes por proprietário:");
        for (String idProprietario : blocosPorProprietario.keySet()) {
            System.out.println("Proprietário " + idProprietario + ": " + blocosPorProprietario.get(idProprietario) + " blocos");
        }
    }

    /**
     * Sugere trocas entre propriedades de diferentes proprietários com base em área e proximidade.
     *
     * @param grafo Grafo de propriedades que contém as conexões entre propriedades vizinhas.
     */
    public static void sugerirTrocas(GrafoPropriedades grafo) {
        System.out.println("\n Sugestões de trocas entre proprietários:");

        for (Propriedade p1 : grafo.getPropriedades()) {
            String dono1 = p1.getIdProprietario();

            for (Propriedade vizinha : grafo.getAdjacentes(p1)) {
                String dono2 = vizinha.getIdProprietario();

                if (!dono1.equals(dono2)) {
                    double areaMediaAtual = p1.getArea();
                    double areaMediaComTroca = vizinha.getArea();
                    double valorAtual = p1.getArea();
                    double valorVizinho = vizinha.getArea();

                    if (areaMediaComTroca > areaMediaAtual && valorVizinho >= valorAtual) {
                        System.out.println("💡 Troca sugerida:");
                        System.out.println("- Proprietário " + dono1 + " troca terreno " + p1.getId() +
                                " com " + dono2 + " (terreno " + vizinha.getId() + ")");
                    }
                }
            }
        }
    }

    /**
     * Identifica e exibe os proprietários que possuem terrenos em mais de um concelho.
     *
     * @param propriedades Lista de propriedades disponíveis.
     */
    public static void proprietariosMulticoncelho(List<Propriedade> propriedades) {
        Map<String, Set<String>> concelhosPorProprietario = new HashMap<>();

        for (Propriedade p : propriedades) {
            String idProprietario = p.getIdProprietario();
            String concelho = p.getConcelho();

            if (concelho != null && !concelho.equalsIgnoreCase("NA") && !concelho.trim().isEmpty()) {
                concelhosPorProprietario
                        .computeIfAbsent(idProprietario, k -> new HashSet<>())
                        .add(concelho);
            }
        }

        System.out.println("\n Proprietários com terrenos em mais do que um concelho:");
        for (Map.Entry<String, Set<String>> entry : concelhosPorProprietario.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println("Proprietário " + entry.getKey() + " está presente em " +
                        entry.getValue().size() + " concelhos: " + entry.getValue());
            }
        }
    }
}
