package fr.univrouen.modele.jeu.partie.strategie;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe représentant une stratégie gagnante basée sur l'alignement de pièces
 * dans un carré tournant pour le niveau 4 du jeu Quarto.
 * Cette stratégie vérifie si un carré tournant formé par 4 pièces, disposées
 * dans une configuration spécifique, contient des pièces ayant la même
 * caractéristique. Un carré tournant est défini par 4 pièces placées dans des
 * positions formant un carré en rotation, par exemple les positions a2, b1, c2, b3.
 * 
 * La méthode de vérification de la victoire considère également la stratégie du
 * niveau 3, dans laquelle les pièces forment un grand carré (3x3).
 * 
 * @see StrategieAbstraite
 * @see StrategieNiveau3
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class StrategieNiveau4 extends StrategieAbstraite {
    private StrategieNiveau3 strategieNiveau3;

    /**
     * Constructeur de la stratégie du niveau 4.
     * Ce constructeur initialise la stratégie de niveau 3 qui sera utilisée 
     * pour vérifier les configurations gagnantes du niveau 3 (grands carrés).
     */
    public StrategieNiveau4() {
        this.strategieNiveau3 = new StrategieNiveau3();
    }

    @Override
    public boolean verifierGagnant(Piece[][] plateau) {
        // Vérification des carrés tournants (comme a2, b1, c2, b3)
        for (int ligne = 0; ligne < 3; ligne++) {
            for (int colonne = 0; colonne < 3; colonne++) {
                if (verifierCarreTournant(plateau, ligne, colonne)) {
                    return true;
                }
            }
        }

        // Vérification des configurations gagnantes du niveau 3
        return strategieNiveau3.verifierGagnant(plateau);
    }

    /**
     * Vérifie si un carré tournant de 4 pièces a des pièces avec la même caractéristique.
     * Un carré tournant est formé par 4 pièces disposées dans une configuration en rotation
     * sur le plateau (exemple : a2, b1, c2, b3).
     * Les 4 pièces doivent partager la même caractéristique pour que l'alignement soit gagnant.
     * 
     * @param plateau Le plateau de jeu (une matrice 4x4 de pièces).
     * @param ligne L'indice de ligne de la pièce en haut à gauche du carré tournant.
     * @param colonne L'indice de colonne de la pièce en haut à gauche du carré tournant.
     * @return vrai si les 4 pièces du carré tournant ont la même caractéristique, sinon faux.
     * @see StrategieAbstraite#verifierCaracteristiques(List<Piece>)
     */
    private boolean verifierCarreTournant(Piece[][] plateau, int ligne, int colonne) {
        // Vérification des indices pour éviter l'accès hors limites du tableau
        if (ligne + 2 >= plateau.length || colonne + 1 >= plateau[0].length || colonne - 1 < 0) {
            return false; // Si les indices sont hors limites, on ne vérifie pas
        }

        // Récupération des pièces
        Piece pieceHauteGauche = plateau[ligne][colonne];
        Piece pieceHauteDroite = plateau[ligne + 1][colonne + 1];
        Piece pieceBasseGauche = plateau[ligne + 1][colonne - 1];
        Piece pieceBasseDroite = plateau[ligne + 2][colonne];

        // Vérification de nullité avant de continuer la vérification
        if (pieceHauteGauche == null || pieceHauteDroite == null || pieceBasseGauche == null || pieceBasseDroite == null) {
            return false; // Si une des pièces est null, la vérification échoue
        }

        // Créer une liste des 4 pièces du carré tournant
        List<Piece> alignement = new ArrayList<>();
        alignement.add(pieceHauteGauche);
        alignement.add(pieceHauteDroite);
        alignement.add(pieceBasseGauche);
        alignement.add(pieceBasseDroite);

        // Vérification des caractéristiques des pièces en passant la liste
        return verifierCaracteristiques(alignement);
    } 
}