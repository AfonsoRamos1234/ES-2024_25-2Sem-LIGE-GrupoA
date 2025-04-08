package com.projeto.territorio;

/**
 * Representa uma sugestão de troca de propriedades entre dois proprietários distintos.
 * Contém as informações sobre os proprietários envolvidos e as propriedades que estão sendo consideradas para troca.
 */
public class TrocaSugerida {
    private String dono1;
    private Propriedade prop1;
    private String dono2;
    private Propriedade prop2;

    /**
     * Cria uma nova sugestão de troca entre duas propriedades pertencentes a diferentes proprietários.
     *
     * @param dono1 ID do primeiro proprietário.
     * @param prop1 Primeira propriedade a ser trocada.
     * @param dono2 ID do segundo proprietário.
     * @param prop2 Segunda propriedade a ser trocada.
     */
    public TrocaSugerida(String dono1, Propriedade prop1, String dono2, Propriedade prop2) {
        this.dono1 = dono1;
        this.prop1 = prop1;
        this.dono2 = dono2;
        this.prop2 = prop2;
    }

    /**
     * Retorna a primeira propriedade da troca sugerida.
     *
     * @return Propriedade do primeiro proprietário.
     */
    public Propriedade getProp1() {
        return prop1;
    }

    /**
     * Retorna a segunda propriedade da troca sugerida.
     *
     * @return Propriedade do segundo proprietário.
     */
    public Propriedade getProp2() {
        return prop2;
    }

    /**
     * Retorna uma descrição textual da sugestão de troca.
     *
     * @return String formatada descrevendo a troca sugerida.
     */
    @Override
    public String toString() {
        return "💡 Troca sugerida:\n- Proprietário " + dono1 + " troca " + prop1.getId() +
                " com Proprietário " + dono2 + " (" + prop2.getId() + ")";
    }

    // Add getters here if needed
}