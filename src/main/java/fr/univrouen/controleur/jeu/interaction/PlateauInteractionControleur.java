package fr.univrouen.controleur.jeu.interaction;

import fr.univrouen.controleur.util.JoueurControleur;
import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.*;
import fr.univrouen.vue.jeu.PiecesRestantesPanel;

/**
 * Contrôleur gérant les interactions de l'utilisateur avec le plateau de jeu.
 * <p>
 * Cette classe est responsable de la logique métier liée au placement
 * des pièces sur le plateau par un joueur humain.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class PlateauInteractionControleur {

    /**
     * Gère le clic d'un joueur humain sur une cellule du plateau.
     * Si la cellule est libre et qu'une pièce est en cours de sélection,
     * le joueur tente de la placer.
     *
     * @param jeu                Le jeu en cours.
     * @param ligne              L'indice de la ligne cliquée.
     * @param colonne            L'indice de la colonne cliquée.
     * @param plateau            Le plateau de jeu contenant les cases.
     * @param panneauPiecesRestantes Le panneau graphique affichant les pièces restantes.
     */
    public static void traiterClicSurPlateau(Jeu jeu, int ligne, int colonne, Plateau plateau,
                                             PiecesRestantesPanel panneauPiecesRestantes) {
        Joueur joueurActuel = jeu.getJoueurActuel();
        if (JoueurControleur.estHumain(joueurActuel) && !jeu.debutPartie()) {
            Position positionCliquee = new Position(ligne, colonne);
            Piece pieceAPlacer = jeu.getPieceCourante();

            if (pieceAPlacer != null && plateau.estLibre(positionCliquee)) {
                panneauPiecesRestantes.removeSelectedPiece(); // Supprime la sélection visuelle
                jeu.getJoueurActuel().placerPiece(jeu, pieceAPlacer, positionCliquee);
            }
        }
    }
}