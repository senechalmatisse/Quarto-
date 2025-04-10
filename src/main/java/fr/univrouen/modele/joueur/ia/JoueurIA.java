package fr.univrouen.modele.joueur.ia;

import fr.univrouen.modele.jeu.partie.*;
import fr.univrouen.modele.jeu.partie.plateau.Position;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.joueur.Joueur;

/**
 * Classe abstraite représentant un joueur de type Intelligence Artificielle (IA).
 * Cette classe implémente l'interface Joueur et fournit une structure pour les algorithmes de l'IA (par exemple, Minimax, Alpha-Beta).
 * 
 * @see Joueur
 * @see Jeu
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public abstract class JoueurIA implements Joueur {

    /**
     * Constructeur d'une IA.
     */
    public JoueurIA() {}

    /**
     * Applique physiquement un placement de pièce sur le plateau.
     *
     * @param jeu      L’état du jeu courant.
     * @param piece    La pièce à placer.
     * @param position La position ciblée.
     */
    protected void appliquerPlacement(Jeu jeu, Piece piece, Position position) {
        jeu.getPlateau().placerPiece(piece, position);
        jeu.retirerPieceChoisit(piece);
        jeu.setPieceCourante(null);

        if (!jeu.estTerminee()) {
            choisirPiece(jeu, null);
        }
    }

    /**
     * Vérifie si la pièce permet à l’adversaire de gagner immédiatement.
     *
     * @param jeu   Le jeu courant.
     * @param piece La pièce testée.
     * @return true si la pièce peut mener à une victoire adverse.
     */
    protected boolean piecePermetVictoireAdversaire(Jeu jeu, Piece piece) {
        for (Position pos : jeu.getPlateau().getPositionsDisponibles()) {
            Jeu simulation = jeu.copier();
            simulation.getPlateau().placerPiece(piece, pos);
            simulation.retirerPieceChoisit(piece);
            simulation.setPieceCourante(null);
            simulation.tourSuivant();

            if (simulation.aGagne() && simulation.getJoueurGagnant().getNom().contains("Humain")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retourne une pièce par défaut si aucun bon coup n’a été trouvé.
     *
     * @param jeu L’état du jeu courant.
     * @return Une pièce disponible, ou null si aucune.
     */
    protected Piece choisirPieceParDefaut(Jeu jeu) {
        return jeu.getPiecesRestantes().isEmpty()
                ? null
                : jeu.getPiecesRestantes().get(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Joueur autre = (Joueur) obj;
        return this.getNom().equals(autre.getNom());
    }
}