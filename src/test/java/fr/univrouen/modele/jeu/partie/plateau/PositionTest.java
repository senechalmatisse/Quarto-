package fr.univrouen.modele.jeu.partie.plateau;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    // Test pour vérifier la création d'une position valide
    @Test
    void testPositionValide() {
        Position position = new Position(1, 2);
        assertEquals(1, position.getX(), "La coordonnée X devrait être 1");
        assertEquals(2, position.getY(), "La coordonnée Y devrait être 2");
    }

    // Test pour vérifier la gestion des coordonnées hors limites
    @Test
    void testCoordonneesHorsLimites() {
        // Test coordonnée X < 0
        assertThrows(IllegalArgumentException.class, () -> new Position(-1, 0), "Une exception devrait être lancée pour x < 0");
        // Test coordonnée X >= 4
        assertThrows(IllegalArgumentException.class, () -> new Position(4, 0), "Une exception devrait être lancée pour x >= 4");
        // Test coordonnée Y < 0
        assertThrows(IllegalArgumentException.class, () -> new Position(0, -1), "Une exception devrait être lancée pour y < 0");
        // Test coordonnée Y >= 4
        assertThrows(IllegalArgumentException.class, () -> new Position(0, 4), "Une exception devrait être lancée pour y >= 4");
    }

    // Test pour vérifier la méthode equals()
    @Test
    void testEquals() {
        Position position1 = new Position(2, 3);
        Position position2 = new Position(2, 3);
        Position position3 = new Position(1, 3);
        
        // Test d'égalité
        assertTrue(position1.equals(position2), "Les positions devraient être égales");
        // Test de non-égalité
        assertFalse(position1.equals(position3), "Les positions ne devraient pas être égales");
    }

    // Test pour vérifier la méthode toString()
    @Test
    void testToString() {
        Position position = new Position(1, 2);
        assertEquals("(1, 2)", position.toString(), "La méthode toString() devrait retourner '(1, 2)'");
    }

    // Test pour vérifier les setters
    @Test
    void testSetters() {
        Position position = new Position(1, 2);
        position.setX(3);
        position.setY(0);
        
        // Vérification des nouvelles coordonnées
        assertEquals(3, position.getX(), "La nouvelle coordonnée X devrait être 3");
        assertEquals(0, position.getY(), "La nouvelle coordonnée Y devrait être 0");
    }
}