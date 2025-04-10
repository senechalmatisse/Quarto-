package fr.univrouen.controleur.jeu.selection;

import java.awt.Color;

import fr.univrouen.controleur.util.JoueurControleur;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Contrôleur centralisant la logique métier liée à la sélection de pièces
 * par les joueurs, ainsi que les effets visuels conditionnels (comme la couleur de survol).
 * <p>
 * Il permet notamment de :
 * <ul>
 *   <li>Valider qu’un joueur humain peut sélectionner une pièce</li>
 *   <li>Déterminer si une pièce peut être retirée du stock</li>
 *   <li>Appliquer un style visuel en fonction du type de joueur</li>
 * </ul>
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class PieceSelectionControleur {

    /**
     * Tente de faire choisir une pièce par le joueur actuel.
     *
     * @param jeu   L’instance du jeu.
     * @param piece La pièce sélectionnée.
     * @return true si la pièce a été sélectionnée avec succès (par un humain), false sinon.
     */
    public static boolean traiterSelection(Jeu jeu, Piece piece) {
        Joueur joueur = jeu.getJoueurActuel();
        if (joueur != null && JoueurControleur.estHumain(joueur)) {
            joueur.choisirPiece(jeu, piece);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la pièce sélectionnée peut être retirée (encore disponible).
     * 
     * @param jeu   L'état du jeu courant.
     * @param piece La pièce potentiellement sélectionnée.
     * @return true si la pièce est encore dans les pièces restantes.
     */
    public static boolean peutRetirer(Jeu jeu, Piece piece) {
        return piece != null && jeu.getPiecesRestantes().contains(piece);
    }

    /**
     * Détermine dynamiquement la couleur de survol à afficher sur le plateau,
     * en fonction du type de joueur (humain ou IA).
     *
     * @param jeu L'état actuel du jeu.
     * @return Une couleur semi-transparente adaptée au joueur :
     *         bleu clair pour humain, rouge clair pour IA.
     */
    public static Color getCouleurSurvolPourJoueur(Jeu jeu) {
        Joueur joueur = jeu.getJoueurActuel();
        if (joueur != null && JoueurControleur.estHumain(joueur)) {
            return new Color(52, 152, 219, 100); // Bleu
        } else {
            return new Color(231, 76, 60, 100); // Rouge
        }
    }
}