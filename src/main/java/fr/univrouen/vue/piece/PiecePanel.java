package fr.univrouen.vue.piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import fr.univrouen.controleur.jeu.interaction.*;
import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.caracteristique.*;

/**
 * Représentation graphique d'une pièce de Quarto.
 * <p>
 * Cette classe est responsable du rendu visuel d'une pièce en fonction de ses caractéristiques :
 * couleur, forme, hauteur et remplissage. Elle permet également de gérer la sélection visuelle
 * et intègre une animation via une mise à l'échelle (zoom).
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.2
 * @since JDK 17
 * @see Piece
 */
public class PiecePanel extends JPanel {
    /** Taille d'affichage d'une pièce de hauteur HAUTE. */
    private final static int TAILLE_PIECE_HAUTE = 100;

    /** Taille d'affichage d'une pièce de hauteur BASSE. */
    private final static int TAILLE_PIECE_BASSE = 50;

    /** Couleur graphique utilisée pour les pièces claires. */
    private final static Color COULEUR_CLAIRE = Color.CYAN;

    /** Couleur graphique utilisée pour les pièces foncées. */
    private final static Color COULEUR_SOMBRE = Color.RED;

    /** Référence vers la pièce du jeu représentée par ce panel. */
    private final Piece piece;

    /** Indique si la pièce est sélectionnée. */
    private boolean estSelectionnee;

    /** Facteur d’échelle utilisé pour les effets de zoom (1.0 = taille normale). */
    private float facteurEchelle = 1.0f;

    /**
     * Construit un panneau représentant une pièce de Quarto.
     * Initialise également le gestionnaire de clics utilisateur.
     * 
     * @param jeu   Le jeu en cours (utilisé pour modifier la pièce courante).
     * @param piece La pièce à représenter.
     */
    public PiecePanel(Jeu jeu, Piece piece) {
        this.piece = piece;
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean nouveauStatut = PiecePanelInteractionControleur.gererClic(jeu, piece, estSelectionnee);
                setSelectionnee(nouveauStatut);
            }
        });
    }

    /**
     * Retourne la pièce associée à ce panneau.
     * 
     * @return La pièce du jeu.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Modifie l'état de sélection du panneau.
     * Déclenche un repaint pour mettre à jour l'apparence.
     * 
     * @param estSelectionnee true si sélectionnée, false sinon.
     */
    public void setSelectionnee(boolean estSelectionnee) {
        this.estSelectionnee = estSelectionnee;
        repaint();
    }

    /**
     * Indique si la pièce est actuellement sélectionnée.
     *
     * @return true si sélectionnée, false sinon.
     */
    public boolean estSelectionnee() {
        return this.estSelectionnee;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int taille = (piece.getHauteur() == Hauteur.HAUTE) ? TAILLE_PIECE_HAUTE : TAILLE_PIECE_BASSE;
        int tailleRedimensionnee = (int) (taille * facteurEchelle);
        int x = (getWidth() - tailleRedimensionnee) / 2;
        int y = (getHeight() - tailleRedimensionnee) / 2;

        // Affichage d’un fond jaune si sélectionnée
        if (estSelectionnee) {
            g2d.setColor(new Color(255, 215, 0));
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        }

        g2d.setColor(piece.getCouleur() == Couleur.CLAIRE ? COULEUR_CLAIRE : COULEUR_SOMBRE);
        dessinerForme(piece, g2d, x, y, tailleRedimensionnee);
        g2d.dispose();
    }

    /**
     * Dessine la forme de la pièce (cercle ou carré).
     *
     * @param piece La pièce à dessiner.
     * @param g2d   Le contexte graphique.
     * @param x     Position X.
     * @param y     Position Y.
     * @param taille Taille de la forme.
     */
    private static void dessinerForme(Piece piece, Graphics2D g2d, int x, int y, int taille) {
        if (piece.getForme() == Forme.RONDE) {
            dessinerCercle(piece, g2d, x, y, taille);
        } else {
            dessinerCarre(piece, g2d, x, y, taille);
        }
    }

    /**
     * Dessine un cercle représentant la pièce.
     *
     * @param piece La pièce.
     * @param g2d   Le contexte graphique.
     * @param x     Coordonnée X.
     * @param y     Coordonnée Y.
     * @param taille Taille du cercle.
     */
    private static void dessinerCercle(Piece piece, Graphics2D g2d, int x, int y, int taille) {
        if (piece.getRemplissage() == Remplissage.PLEIN) {
            g2d.fillOval(x, y, taille, taille);
        } else {
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, taille, taille);
        }
    }

    /**
     * Dessine un carré représentant la pièce.
     *
     * @param piece La pièce.
     * @param g2d   Le contexte graphique.
     * @param x     Coordonnée X.
     * @param y     Coordonnée Y.
     * @param taille Taille du carré.
     */
    private static void dessinerCarre(Piece piece, Graphics2D g2d, int x, int y, int taille) {
        if (piece.getRemplissage() == Remplissage.PLEIN) {
            g2d.fillRect(x, y, taille, taille);
        } else {
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, taille, taille);
        }
    }

    /**
     * Dessine une pièce sur un plateau extérieur (utilisé dans PlateauPanel).
     *
     * @param g2d      Contexte graphique.
     * @param piece    La pièce à dessiner.
     * @param x        Coordonnée X.
     * @param y        Coordonnée Y.
     * @param cellSize Taille de la cellule du plateau.
     */
    public static void drawPiece(Graphics2D g2d, Piece piece, int x, int y, int cellSize) {
        int taille = piece.getHauteur() == Hauteur.HAUTE ? TAILLE_PIECE_HAUTE : TAILLE_PIECE_BASSE;
        int pieceX = x + (cellSize - taille) / 2;
        int pieceY = y + (cellSize - taille) / 2;
        dessinerPiece(g2d, piece, pieceX, pieceY, taille);
    }

    /**
     * Méthode utilitaire pour dessiner une pièce à une position donnée.
     *
     * @param g      Contexte graphique.
     * @param piece  La pièce à dessiner.
     * @param x      Coordonnée X.
     * @param y      Coordonnée Y.
     * @param taille Taille de la pièce.
     */
    private static void dessinerPiece(Graphics g, Piece piece, int x, int y, int taille) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(piece.getCouleur() == Couleur.CLAIRE ? COULEUR_CLAIRE : COULEUR_SOMBRE);
        dessinerForme(piece, g2d, x, y, taille);
    }
}