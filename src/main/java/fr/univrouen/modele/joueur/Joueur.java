package fr.univrouen.modele.joueur;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Interface représentant un joueur dans le jeu Quarto.
 * <p>
 * Un joueur peut être un humain ou une intelligence artificielle (IA).
 * Il doit être capable de placer une pièce sur le plateau et de choisir une pièce
 * à donner à l'adversaire.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public interface Joueur {

    /**
     * Retourne le nom du joueur, qui permet d’identifier s’il s’agit d’un humain ou d’une IA.
     * 
     * @return Le nom du joueur.
     */
    String getNom();

    /**
     * Permet au joueur de placer une pièce sur le plateau.
     * Cette méthode est appelée lorsque le joueur a une pièce à poser
     * et choisit une position libre sur le plateau.
     * 
     * @param jeu      L’état actuel du jeu.
     * @param piece    La pièce à placer.
     * @param position La position sur le plateau où placer la pièce.
     */
    void placerPiece(Jeu jeu, Piece piece, Position position);

    /**
     * Permet au joueur de choisir une pièce à donner à son adversaire.
     * Cette méthode est appelée après qu’un joueur ait placé une pièce
     * et doit sélectionner la prochaine pièce à jouer.
     * 
     * @param jeu          L’état actuel du jeu.
     * @param pieceChoisit La pièce sélectionnée à remettre à l’adversaire.
     */
    void choisirPiece(Jeu jeu, Piece pieceChoisit);
}