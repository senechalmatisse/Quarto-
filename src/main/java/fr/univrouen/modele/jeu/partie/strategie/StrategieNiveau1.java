package fr.univrouen.modele.jeu.partie.strategie;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe représentant une stratégie gagnante basée sur l'alignement de 4 pièces ayant un caractère en commun
 * dans le jeu Quarto!.
 * Cette stratégie vérifie si un alignement de quatre pièces, disposées horizontalement, verticalement ou diagonalement,
 * contient des pièces ayant une caractéristique en commun (par exemple la même hauteur, couleur, etc.).
 * 
 * <p>Pour un alignement de 4 pièces d'une caractéristique commune, la méthode {@link #verifierGagnant(Piece[][])} 
 * renverra vrai, indiquant qu'il y a une condition gagnante sur le plateau.</p>
 * 
 * @see StrategieAbstraite
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class StrategieNiveau1 extends StrategieAbstraite {

    @Override
    public boolean verifierGagnant(Piece[][] plateauDeJeu) {
        // Parcours du plateau pour vérifier chaque pièce en tant que début d'un alignement possible
        for (int ligne = 0; ligne < 4; ligne++) {
            for (int colonne = 0; colonne < 4; colonne++) {
                Piece pieceActuelle = plateauDeJeu[ligne][colonne];
                // Vérification si l'alignement est possible à partir de cette pièce
                if (pieceActuelle != null && verifierAlignement(pieceActuelle, plateauDeJeu, ligne, colonne)) {
                    return true; // Alignement trouvé
                }
            }
        }

        return false; // Aucun alignement trouvé
    }
    
    /**
     * Vérifie si un alignement de quatre pièces est possible à partir de la pièce donnée.
     * Vérifie l'alignement horizontal, vertical et diagonal dans les deux directions possibles.
     * 
     * @param pieceActuelle La pièce à partir de laquelle l'alignement est vérifié.
     * @param plateauDeJeu Le plateau de jeu (matrice 4x4).
     * @param ligne La ligne de la pièce actuelle.
     * @param colonne La colonne de la pièce actuelle.
     * @return vrai si un alignement de quatre pièces avec la même caractéristique est trouvé, sinon faux.
     */
    private boolean verifierAlignement(Piece pieceActuelle, Piece[][] plateauDeJeu, int ligne, int colonne) {
        // Vérification des alignements dans les différentes directions
        return verifierAlignementDansDirection(plateauDeJeu, ligne, colonne, 1, 0) || // Horizontal
               verifierAlignementDansDirection(plateauDeJeu, ligne, colonne, 0, 1) || // Vertical
               verifierAlignementDansDirection(plateauDeJeu, ligne, colonne, 1, 1) || // Diagonal descendante
               verifierAlignementDansDirection(plateauDeJeu, ligne, colonne, 1, -1);  // Diagonal montante
    }
    
    /**
     * Vérifie un alignement de 4 pièces dans la direction spécifiée.
     * 
     * @param plateauDeJeu Le plateau de jeu (matrice 4x4).
     * @param ligne La ligne de la pièce de départ.
     * @param colonne La colonne de la pièce de départ.
     * @param deltaLigne Déplacement de ligne pour l'alignement.
     * @param deltaColonne Déplacement de colonne pour l'alignement.
     * @return vrai si l'alignement est valide, sinon faux.
     */
    private boolean verifierAlignementDansDirection(Piece[][] plateauDeJeu, int ligne, int colonne, 
                                                    int deltaLigne, int deltaColonne) {
        List<Piece> alignement = new ArrayList<>();

        // Collecter les 4 pièces dans l'alignement
        for (int i = 0; i < 4; i++) {
            int nouvelleLigne = ligne + deltaLigne * i;
            int nouvelleColonne = colonne + deltaColonne * i;

            // Vérifier si on est toujours dans les limites du plateau
            if (nouvelleLigne < 0 || nouvelleLigne >= 4 || nouvelleColonne < 0 || nouvelleColonne >= 4) {
                return false;
            }

            if (plateauDeJeu[nouvelleLigne][nouvelleColonne] == null) {
                return false;
            }

            // Ajouter la pièce actuelle dans la liste d'alignement
            alignement.add(plateauDeJeu[nouvelleLigne][nouvelleColonne]);
        }

        // Vérifier si toutes les pièces de l'alignement ont les mêmes caractéristiques
        return verifierCaracteristiques(alignement);
    }
}