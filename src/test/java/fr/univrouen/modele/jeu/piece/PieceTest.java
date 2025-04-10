package fr.univrouen.modele.jeu.piece;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.univrouen.modele.jeu.piece.caracteristique.*;

/**
 * Classe de test pour la classe Piece.
 */
public class PieceTest {

    /**
     * Test du constructeur et des getters.
     */
    @Test
    void testPieceConstructorAndGetters() {
        // Création d'une pièce avec des valeurs spécifiques
        Piece piece = new Piece(Hauteur.HAUTE, Forme.RONDE, Remplissage.PLEIN, Couleur.CLAIRE);

        // Vérification des valeurs des attributs
        assertEquals(Hauteur.HAUTE, piece.getHauteur());
        assertEquals(Forme.RONDE, piece.getForme());
        assertEquals(Remplissage.PLEIN, piece.getRemplissage());
        assertEquals(Couleur.CLAIRE, piece.getCouleur());
    }

    /**
     * Test de l'exception dans le constructeur si un paramètre est nul.
     */
    @Test
    void testConstructorWithNullValues() {
        // Vérification qu'une exception est levée si un attribut est nul
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(null, Forme.RONDE, Remplissage.PLEIN, Couleur.CLAIRE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(Hauteur.HAUTE, null, Remplissage.PLEIN, Couleur.CLAIRE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(Hauteur.HAUTE, Forme.RONDE, null, Couleur.CLAIRE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Piece(Hauteur.HAUTE, Forme.RONDE, Remplissage.PLEIN, null);
        });
    }

    /**
     * Test de la méthode toString.
     */
    @Test
    void testToString() {
        Piece piece = new Piece(Hauteur.HAUTE, Forme.CARREE, Remplissage.CREUX, Couleur.FONCEE);
        String expected = "Piece(HAUTE, CARREE, CREUX, FONCEE)";
        assertEquals(expected, piece.toString());
    }

    /**
     * Test de la méthode equals.
     */
    @Test
    void testEquals() {
        Piece piece1 = new Piece(Hauteur.HAUTE, Forme.RONDE, Remplissage.PLEIN, Couleur.CLAIRE);
        Piece piece2 = new Piece(Hauteur.HAUTE, Forme.RONDE, Remplissage.PLEIN, Couleur.CLAIRE);
        Piece piece3 = new Piece(Hauteur.BASSE, Forme.CARREE, Remplissage.CREUX, Couleur.FONCEE);

        // Vérification de l'égalité entre pièces identiques
        assertTrue(piece1.equals(piece2));

        // Vérification de l'inégalité entre pièces différentes
        assertFalse(piece1.equals(piece3));

        // Vérification de l'égalité avec l'objet lui-même
        assertTrue(piece1.equals(piece1));

        // Vérification avec un objet null
        assertFalse(piece1.equals(null));

        // Vérification avec un objet d'une classe différente
        assertFalse(piece1.equals("test"));
    }
}