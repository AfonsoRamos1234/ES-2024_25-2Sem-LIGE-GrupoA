package com.projeto.territorio;

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

    public String getId() { return id; }
    public double getArea() { return area; }
    public String getIdProprietario() { return idProprietario; }
    public double getX() { return x; }
    public double getY() { return y; }
    public String getFreguesia() { return freguesia; }
    public String getConcelho() { return concelho; }
    public String getDistrito() { return distrito; }

    public double distancia(Propriedade outra) {
        double dx = this.x - outra.x;
        double dy = this.y - outra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

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
}

