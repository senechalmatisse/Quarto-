package fr.univrouen.vue.jeu;

import javax.swing.*;

import java.util.*;
import java.util.List;

import java.awt.*;
import java.awt.event.*;

import fr.univrouen.controleur.jeu.selection.*;
import fr.univrouen.controleur.util.JoueurControleur;
import fr.univrouen.controleur.jeu.interaction.PiecesRestantesInteractionControleur;
import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.observer.Observer;
import fr.univrouen.vue.piece.PiecePanel;

/**
 * Panneau graphique Swing représentant les pièces restantes disponibles dans le jeu Quarto.
 * <p>
 * Ce composant gère l'affichage dynamique des pièces encore jouables, permet aux joueurs humains
 * de sélectionner une pièce, et synchronise l'affichage avec l'état du modèle {@link Jeu}.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 2.0
 * @since JDK 17
 * @see Piece
 */
public class PiecesRestantesPanel extends JPanel implements Observer {
    /** Référence vers le modèle du jeu. */
    private Jeu jeu;

    /** Liste des composants visuels associés à chaque pièce encore disponible. */
    private final List<PiecePanel> listePanels = new ArrayList<>();

    /** Contrôleur de gestion de la sélection unique d'une pièce. */
    private final PiecePanelSelectionControleur selectionControleur = new PiecePanelSelectionControleur();

    /**
     * Construit le panneau des pièces restantes en initialisant leur affichage
     * et en enregistrant l'observateur pour suivre les changements du modèle.
     *
     * @param jeu L’instance du jeu à observer et à représenter visuellement.
     */
    public PiecesRestantesPanel(Jeu jeu) {
        this.jeu = jeu;

        setOpaque(false);
        setLayout(new GridLayout(4, 4));
        setPreferredSize(new Dimension(500, 150));

        ToolTipManager.sharedInstance().setInitialDelay(0);

        for (Piece piece : jeu.getPiecesRestantes()) {
            ajouterPiecePanel(piece);
        }
    }

    /**
     * Ajoute dynamiquement un panel représentant une pièce dans la grille d'affichage,
     * tout en y associant les comportements utilisateur (clic, survol...).
     *
     * @param piece La pièce à représenter dans l'interface.
     */
    private void ajouterPiecePanel(Piece piece) {
        PiecePanel panel = new PiecePanel(jeu, piece);
        listePanels.add(panel);
        add(panel);

        // Infobulle contenant les caractéristiques de la pièce
        panel.setToolTipText(piece.toString());

        // Gestion des événements de survol et de clic
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!PiecesRestantesInteractionControleur.peutSurvoler(jeu)) return;
                Color survolColor = PieceSelectionControleur.getCouleurSurvolPourJoueur(jeu);
                panel.setBorder(BorderFactory.createLineBorder(survolColor, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!panel.estSelectionnee()) {
                    panel.setBorder(null);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!JoueurControleur.estHumain(jeu.getJoueurActuel())) return;
                if (PieceSelectionControleur.traiterSelection(jeu, piece)) {
                    selectionControleur.mettreAJourSelection(panel);
                }
            }
        });
    }

    /**
     * Supprime de l'interface la pièce actuellement sélectionnée, 
     * si elle est encore considérée comme valide dans le modèle du jeu.
     * <p>
     * Cette méthode est généralement appelée après un placement de pièce sur le plateau.
     * </p>
     */
    public void removeSelectedPiece() {
        PiecePanel panel = selectionControleur.getSelection();
        if (panel == null) return;

        Piece piece = panel.getPiece();
        if (PieceSelectionControleur.peutRetirer(jeu, piece)) {
            listePanels.remove(panel);
            remove(panel);
            selectionControleur.reinitialiser();
            revalidate();
            repaint();
        }
    }

    @Override
    public void update() {
        List<Piece> aSupprimer = PiecesRestantesInteractionControleur.calculerPiecesSupprimees(
            listePanels.stream().map(PiecePanel::getPiece).toList(), jeu
        );
        List<PiecePanel> panelsASupprimer = new ArrayList<>();

        for (PiecePanel panel : listePanels) {
            if (aSupprimer.contains(panel.getPiece())) {
                panelsASupprimer.add(panel);
            }
        }

        for (PiecePanel panel : panelsASupprimer) {
            listePanels.remove(panel);
            remove(panel);
        }

        // Mise à jour de la pièce sélectionnée visuellement
        Piece pieceCourante = jeu.getPieceCourante();
        for (PiecePanel panel : listePanels) {
            panel.setSelectionnee(panel.getPiece().equals(pieceCourante));
        }

        revalidate();
        repaint();
    }
}