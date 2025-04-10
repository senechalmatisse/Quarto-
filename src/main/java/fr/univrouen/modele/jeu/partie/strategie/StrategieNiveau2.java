package fr.univrouen.modele.jeu.partie.strategie;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe représentant une stratégie gagnante basée sur l'alignement de 4 pièces ayant un caractère en commun
 * dans un petit carré du jeu Quarto!.
 * Cette stratégie vérifie si un petit carré formé par 4 pièces disposées dans une configuration spécifique
 * (par exemple a1, a2, b1, b2) contient des pièces ayant une caractéristique en commun (par exemple la même hauteur, couleur, ...).
 * 
 * <p>Les configurations gagnantes pour ce niveau sont celles du niveau 1, mais les pièces sont placées dans un petit carré 
 * de la forme a1, a2, b1, b2 ou b2, b3, c2, c3.</p>
 * <p>Elle vérifie également les configurations gagnantes du niveau 1.</p>
 * 
 * @see StrategieAbstraite
 * @see StrategieNiveau1
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class StrategieNiveau2 extends StrategieAbstraite {
    private StrategieNiveau1 strategieNiveau1;

    /**
     * Constructeur par défaut qui initialise la stratégie de niveau 1.
     * Cette stratégie de niveau 1 est utilisée pour vérifier les configurations gagnantes classiques du niveau 1.
     */
    public StrategieNiveau2() {
        this.strategieNiveau1 = new StrategieNiveau1();
    }

    @Override
    public boolean verifierGagnant(Piece[][] plateau) {
        // Vérifier les petits carrés (niveau 2) et les configurations de niveau 1
        for (int ligne = 0; ligne < 3; ligne++) {
            for (int colonne = 0; colonne < 3; colonne++) {
                if (verifierPetitCarre(plateau, ligne, colonne)) {
                    return true; // Un petit carré gagnant a été trouvé
                }
            }
        }

        // Vérification des configurations gagnantes du niveau 1
        return strategieNiveau1.verifierGagnant(plateau);
    }

    /**
     * Vérifie si un petit carré de 4 pièces a des pièces avec la même caractéristique.
     * Un petit carré est formé par 4 pièces disposées dans une configuration spécifique,
     * par exemple a1, a2, b1, b2 ou b2, b3, c2, c3.
     * 
     * @param plateau Le plateau de jeu, une matrice 4x4 représentant les pièces.
     * @param ligne L'indice de ligne de la pièce en haut à gauche du carré.
     * @param colonne L'indice de colonne de la pièce en haut à gauche du carré.
     * @return vrai si les 4 pièces du petit carré ont la même caractéristique, sinon faux.
     * @see StrategieAbstraite#verifierCaracteristiques(List<Piece>)
     */
    private boolean verifierPetitCarre(Piece[][] plateau, int ligne, int colonne) {
        // Récupère les 4 pièces du grand carré (2x2)
        Piece pieceHautGauche = plateau[ligne][colonne];
        Piece pieceHautDroit = plateau[ligne][colonne + 1];
        Piece pieceBasGauche = plateau[ligne + 1][colonne];
        Piece pieceBasDroit = plateau[ligne + 1][colonne + 1];

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