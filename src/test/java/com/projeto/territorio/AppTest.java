package com.projeto.territorio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testDistanciaEntrePropriedades() {
        Propriedade p1 = new Propriedade("1", 1000.0, "93", 0.0, 0.0, "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");
        Propriedade p2 = new Propriedade("2", 500.0, "93", 3.0, 4.0, "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");
        double distancia = p1.distancia(p2);
        assertEquals(5.0, distancia, 0.0001); // 3-4-5 triângulo
    }

    @Test
    public void testGetters() {
        Propriedade p = new Propriedade("3", 800.0, "99", 1.0, 1.0, "Funchal", "Funchal", "Madeira");
        assertEquals("3", p.getId());
        assertEquals(800.0, p.getArea());
        assertEquals("99", p.getIdProprietario());
        assertEquals(1.0, p.getX());
        assertEquals(1.0, p.getY());
        assertEquals("Funchal", p.getFreguesia());
        assertEquals("Funchal", p.getConcelho());
        assertEquals("Madeira", p.getDistrito());
    }

    @Test
    public void testToString() {
        Propriedade p = new Propriedade("4", 900.0, "100", 2.0, 2.0, "Porto Moniz", "Porto Moniz", "Madeira");
        String esperado = "Propriedade{id='4', area=900.0, idProprietario='100', freguesia='Porto Moniz', concelho='Porto Moniz', distrito='Madeira'}";
        assertEquals(esperado, p.toString());
    }
}
