package fr.univrouen.modele.jeu.partie.strategie;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe représentant une stratégie gagnante basée sur l'alignement de pièces
 * dans un grand carré pour le niveau 3 du jeu Quarto.
 * Cette stratégie vérifie si un grand carré formé par 4 pièces, disposées
 * dans une configuration spécifique, contient des pièces ayant la même
 * caractéristique. Un grand carré est défini par 4 pièces aux coins d'un carré
 * 3x3, tel que les positions a1, a3, c1, c3 par exemple.
 * 
 * La méthode de vérification de la victoire considère également la stratégie du
 * niveau 2, dans laquelle les pièces forment un petit carré (2x2).
 * 
 * @see StrategieAbstraite
 * @see StrategieNiveau2
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class StrategieNiveau3 extends StrategieAbstraite {
    private StrategieNiveau2 strategieNiveau2;

    /**
     * Constructeur par défaut qui initialise la stratégie du niveau 2.
     * Cette stratégie est utilisée pour vérifier les configurations gagnantes 
     * du niveau 2, incluant les petits carrés (2x2).
     */
    public StrategieNiveau3() {
        this.strategieNiveau2 = new StrategieNiveau2();
    }

    @Override
    public boolean verifierGagnant(Piece[][] plateau) {
        // Vérification des grands carrés (comme a1, a3, c1, c3)
        for (int ligne = 0; ligne < 2; ligne++) {
            for (int colonne = 0; colonne < 2; colonne++) {
                if (verifierGrandCarre(plateau, ligne, colonne)) {
                    return true; // Un grand carré gagnant a été trouvé
                }
            }
        }

        // Vérification des configurations gagnantes du niveau 2
        return strategieNiveau2.verifierGagnant(plateau);
    }

    /**
     * Vérifie si un grand carré de 4 pièces a des pièces avec la même caractéristique.
     * Un grand carré est formé par 4 pièces disposées aux coins d'un carré 3x3 dans le plateau.
     * Les 4 pièces doivent partager la même caractéristique pour que l'alignement soit gagnant.
     * 
     * @param plateau Le plateau de jeu (une matrice 4x4 de pièces).
     * @param ligne L'indice de ligne de la pièce en haut à gauche du grand carré.
     * @param colonne L'indice de colonne de la pièce en haut à gauche du grand carré.
     * @return vrai si les 4 pièces du grand carré ont la même caractéristique, sinon faux.
     * @see StrategieAbstraite#verifierCaracteristiques(List<Piece>)
     */
    private boolean verifierGrandCarre(Piece[][] plateau, int ligne, int colonne) {
        // Récupère les 4 pièces du grand carré (coins du carré 3x3)
        Piece pieceHautGauche = plateau[ligne][colonne];
        Piece pieceHautDroit = plateau[ligne][colonne + 2];
        Piece pieceBasGauche = plateau[ligne + 2][colonne];
        Piece pieceBasDroit = plateau[ligne + 2][colonne + 2];

        // Crée une liste de ces 4 pièces
        List<Piece> alignement = new ArrayList<>();
        alignement.add(pieceHautGauche);
        alignement.add(pieceHautDroit);
        alignement.add(pieceBasGauche);
        alignement.add(pieceBasDroit);

        // Vérifie si toutes les pièces sont non nulles et si elles partagent la même caractéristique
        if (pieceHautGauche != null && pieceHautDroit != null &&
            pieceBasGauche != null && pieceBasDroit != null) {
            return verifierCaracteristiques(alignement);
        }

        // Si une des pièces est nulle, retourne false
        return false;
    }
}