package fr.univrouen.controleur.jeu.interaction;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.vue.piece.PiecePanel;
import fr.univrouen.controleur.util.JoueurControleur;

/**
 * Contrôleur gérant les interactions entre l'utilisateur et une pièce via un {@link PiecePanel}.
 * <p>
 * Il permet notamment de valider une sélection de pièce pour un joueur humain,
 * et d'enregistrer cette pièce dans le modèle du jeu.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class PiecePanelInteractionControleur {

    /**
     * Gère le clic sur une pièce depuis son interface graphique.
     *
     * @param jeu   Le modèle du jeu.
     * @param piece La pièce représentée dans le panel.
     * @param panelSelectionne Indique si la pièce est actuellement sélectionnée.
     * @return true si l'état de sélection doit changer, false sinon.
     */
    public static boolean gererClic(Jeu jeu, Piece piece, boolean panelSelectionne) {
        if (!JoueurControleur.estHumain(jeu.getJoueurActuel())) return false;

        boolean nouveauStatut = !panelSelectionne;
        jeu.setPieceCourante(nouveauStatut ? piece : null);
        return nouveauStatut;
    }
}