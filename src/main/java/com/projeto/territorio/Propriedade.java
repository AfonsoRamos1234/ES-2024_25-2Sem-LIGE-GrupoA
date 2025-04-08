package com.projeto.territorio;

import java.util.Objects;

/**
 * Representa uma propriedade com informações geográficas e de posse.
 * Inclui dados como área, localização (freguesia, concelho, distrito), coordenadas e ID do proprietário.
 */
public class Propriedade {
    private String id;
    private double area;
    private String idProprietario;
    private double x;
    private double y;
    private String freguesia;
    private String concelho;
    private String distrito;

    public Propriedade(String id, double area, String idProprietario, double x, double y,
                       String freguesia, String concelho, String distrito) {
        this.id = id;
        this.area = area;
        this.idProprietario = idProprietario;
        this.x = x;
        this.y = y;
        this.freguesia = freguesia;
        this.concelho = concelho;
        this.distrito = distrito;
    }

    /**
     * Retorna o ID único da propriedade.
     *
     * @return ID da propriedade.
     */
    public String getId() { return id; }

    /**
     * Retorna a área da propriedade.
     *
     * @return Área da propriedade.
     */
    public double getArea() { return area; }

    /**
     * Retorna o ID do proprietário da propriedade.
     *
     * @return ID do proprietário.
     */
    public String getIdProprietario() { return idProprietario; }

    /**
     * Retorna a coordenada X da propriedade.
     *
     * @return Valor X.
     */
    public double getX() { return x; }

    /**
     * Retorna a coordenada Y da propriedade.
     *
     * @return Valor Y.
     */
    public double getY() { return y; }

    /**
     * Retorna o nome da freguesia onde a propriedade está localizada.
     *
     * @return Nome da freguesia.
     */
    public String getFreguesia() { return freguesia; }

    /**
     * Retorna o nome do concelho onde a propriedade está localizada.
     *
     * @return Nome do concelho.
     */
    public String getConcelho() { return concelho; }

    /**
     * Retorna o nome do distrito onde a propriedade está localizada.
     *
     * @return Nome do distrito.
     */
    public String getDistrito() { return distrito; }

    /**
     * Calcula a distância euclidiana entre esta propriedade e outra.
     *
     * @param outra Outra propriedade.
     * @return Distância entre as duas propriedades.
     */
    public double distancia(Propriedade outra) {
        double dx = this.x - outra.x;
        double dy = this.y - outra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Retorna uma representação textual da propriedade.
     *
     * @return String representando a propriedade.
     */
    @Override
    public String toString() {
        return "Propriedade{" +
                "id='" + id + '\'' +
                ", area=" + area +
                ", idProprietario='" + idProprietario + '\'' +
                ", freguesia='" + freguesia + '\'' +
                ", concelho='" + concelho + '\'' +
                ", distrito='" + distrito + '\'' +
                '}';
    }

    /**
     * Compara se duas propriedades são iguais com base no seu ID.
     *
     * @param o Objeto a ser comparado.
     * @return true se os IDs forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propriedade that = (Propriedade) o;
        return Objects.equals(this.getId(), that.getId());
    }

    /**
     * Retorna o código hash baseado no ID da propriedade.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
