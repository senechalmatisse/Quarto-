package fr.univrouen.modele.joueur.humain;

import fr.univrouen.modele.jeu.partie.plateau.*;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.partie.*;

/**
 * Implémentation d’un joueur humain dans le jeu Quarto.
 * <p>
 * Cette classe représente un joueur contrôlé manuellement (via {@link Joueur}).
 * Elle applique les actions demandées par l'utilisateur via l'interface graphique.
 * </p>
 * 
 * <ul>
 *   <li>Lors du placement, elle place la pièce sélectionnée sur le plateau.</li>
 *   <li>Lors du choix de pièce, elle définit la prochaine pièce que devra jouer l’adversaire.</li>
 * </ul>
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class JoueurHumain implements Joueur {

    /**
     * Constructeur par défaut du joueur humain.
     */
    public JoueurHumain() {}

    @Override
    public String getNom() {
        return "Joueur Humain";
    }

    @Override
    public void placerPiece(Jeu jeu, Piece piece, Position position) {
        Plateau plateauDeJeu = jeu.getPlateau();

        plateauDeJeu.placerPiece(piece, position);
        jeu.retirerPieceChoisit(piece);
        jeu.setPieceCourante(null);
    }

    @Override
    public void choisirPiece(Jeu jeu, Piece pieceChoisit) {
        if(pieceChoisit != null) {
            jeu.setPieceCourante(pieceChoisit);
            jeu.tourSuivant();
        }
    }

    @Override
    public String toString() {
        return "C'est le tour du joueur Humain";
    }
}