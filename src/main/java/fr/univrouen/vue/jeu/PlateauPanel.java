package fr.univrouen.vue.jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import fr.univrouen.vue.piece.PiecePanel;
import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.*;
import fr.univrouen.modele.jeu.partie.observer.Observer;
import fr.univrouen.controleur.jeu.interaction.*;
import fr.univrouen.controleur.jeu.selection.PieceSelectionControleur;

/**
 * Panneau graphique représentant le plateau de jeu de Quarto.
 * Gère les clics du joueur humain, le dessin des pièces, des cases et l’effet de survol dynamique.
 * 
 * @author Matisse SENECHAL
 * @version 1.4
 * @since JDK 17
 */
public class PlateauPanel extends JPanel implements Observer {
    /** Référence vers le jeu en cours. */
    private final Jeu jeu;

    /** Référence vers le plateau de jeu. */
    private final Plateau plateau;

    /** Panneau des pièces restantes (pour suppression après placement). */
    private final PiecesRestantesPanel piecesRestantesPanel;

    /** Coordonnée (ligne, colonne) de la case survolée. */
    private Point caseSurvolee = null;

    /**
     * Initialise le panneau graphique du plateau.
     *
     * @param jeu Le jeu actuel.
     * @param piecesRestantesPanel Le panneau associé aux pièces restantes.
     */
    public PlateauPanel(Jeu jeu, PiecesRestantesPanel piecesRestantesPanel) {
        this.jeu = jeu;
        this.plateau = jeu.getPlateau();
        this.piecesRestantesPanel = piecesRestantesPanel;

        setPreferredSize(new Dimension(500, 500));
        setOpaque(false);

        addMouseListener(creerEcouteurClicEtSortie());
        addMouseMotionListener(creerEcouteurSurvol());
    }

    /**
     * Gère les clics sur le plateau ET la sortie de souris du panel.
     */
    private MouseListener creerEcouteurClicEtSortie() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tailleCase = getWidth() / Plateau.TAILLE;
                int ligne = e.getY() / tailleCase;
                int colonne = e.getX() / tailleCase;

                PlateauInteractionControleur.traiterClicSurPlateau(jeu, ligne, colonne, plateau, piecesRestantesPanel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                caseSurvolee = null;
                repaint();
            }
        };
    }

    /**
     * Crée l’écouteur de survol pour afficher une case survolée si libre.
     */
    private MouseMotionListener creerEcouteurSurvol() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int tailleCase = getWidth() / Plateau.TAILLE;
                int ligne = e.getY() / tailleCase;
                int colonne = e.getX() / tailleCase;
                Position position = new Position(ligne, colonne);

                if (plateau.estLibre(position)) {
                    caseSurvolee = new Point(ligne, colonne);
                } else {
                    caseSurvolee = null;
                }

                repaint();
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        int tailleCase = getWidth() / Plateau.TAILLE;

        for (int i = 0; i < Plateau.TAILLE; i++) {
            for (int j = 0; j < Plateau.TAILLE; j++) {
                int x = j * tailleCase;
                int y = i * tailleCase;

                // Effet de survol coloré
                if (caseSurvolee != null && caseSurvolee.x == i && caseSurvolee.y == j) {
                    g2.setColor(getCouleurSurvol());
                    g2.fillRect(x, y, tailleCase, tailleCase);
                }

                g2.setColor(Color.WHITE);
                g2.drawRect(x, y, tailleCase, tailleCase);

                Piece piece = plateau.getPiece(new Position(i, j));
                if (piece != null) {
                    PiecePanel.drawPiece(g2, piece, x, y, tailleCase);
                }
            }
        }
    }

    /**
     * Retourne la couleur de survol dynamique selon si le joueur est humain ou IA.
     *
     * @return Une couleur semi-transparente.
     */
    private Color getCouleurSurvol() {
        return PieceSelectionControleur.getCouleurSurvolPourJoueur(jeu);
    }

    @Override
    public void update() {
        repaint();
        if (jeu != null && jeu.estTerminee()) {
            FinPartieControleur.traiterFinPartie(this, jeu);
        }
    }
}