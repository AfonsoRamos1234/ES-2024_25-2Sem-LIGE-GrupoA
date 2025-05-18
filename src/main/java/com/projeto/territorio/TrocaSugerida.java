package com.projeto.territorio;

/**
 * Representa uma sugestão de troca entre duas propriedades, com critérios de proximidade e semelhança de valor.
 */
public class TrocaSugerida {
    private Propriedade propriedade1;
    private Propriedade propriedade2;
    private double diferencaArea;
    private double distancia;

    public TrocaSugerida(Propriedade propriedade1, Propriedade propriedade2) {
        this.propriedade1 = propriedade1;
        this.propriedade2 = propriedade2;
        this.diferencaArea = Math.abs(propriedade1.getArea() - propriedade2.getArea());
        this.distancia = calcularDistancia(propriedade1, propriedade2);
    }

    public Propriedade getPropriedade1() {
        return propriedade1;
    }

    public Propriedade getPropriedade2() {
        return propriedade2;
    }

    public double getDiferencaArea() {
        return diferencaArea;
    }

    public double getDistancia() {
        return distancia;
    }

    private double calcularDistancia(Propriedade p1, Propriedade p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "Troca entre " + propriedade1.getId() + " e " + propriedade2.getId() +
                " | Diferença de área: " + diferencaArea +
                " | Distância: " + distancia;
    }
}
