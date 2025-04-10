package fr.univrouen.controleur.jeu.selection;

import fr.univrouen.vue.piece.PiecePanel;

/**
 * Contrôleur chargé de gérer la sélection graphique d’un {@link PiecePanel}.
 * <p>
 * Il garantit qu’un seul panel est visuellement sélectionné à la fois,
 * en désélectionnant le précédent automatiquement.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class PiecePanelSelectionControleur {

    /**
     * Panel actuellement sélectionné (visuellement).
     * Peut être {@code null} si aucune pièce n'est sélectionnée.
     */
    private PiecePanel selected;

    /**
     * Sélectionne un nouveau panel et désélectionne le précédent le cas échéant.
     *
     * @param nouveau Le nouveau panel à marquer comme sélectionné.
     */
    public void mettreAJourSelection(PiecePanel nouveau) {
        if (selected != null) {
            selected.setSelectionnee(false);
        }
        nouveau.setSelectionnee(true);
        selected = nouveau;
    }

    /**
     * Retourne le panel actuellement sélectionné.
     *
     * @return Le {@link PiecePanel} sélectionné, ou {@code null} s’il n’y en a aucun.
     */
    public PiecePanel getSelection() {
        return selected;
    }

    /**
     * Désélectionne le panel actuellement actif, s’il y en a un.
     */
    public void reinitialiser() {
        if (selected != null) {
            selected.setSelectionnee(false);
            selected = null;
        }
    }
}