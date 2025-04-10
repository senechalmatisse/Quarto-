package fr.univrouen.controleur.jeu.interaction;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.controleur.util.JoueurControleur;
import fr.univrouen.modele.jeu.partie.Jeu;

/**
 * Contrôleur gérant les règles d’interaction avec les pièces restantes du jeu.
 * <p>
 * Cette classe regroupe la logique permettant de savoir quand un joueur peut interagir
 * avec les pièces restantes (survol) et quelles pièces doivent être retirées
 * de l’interface graphique en fonction de l’état du modèle.
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class PiecesRestantesInteractionControleur {

    /**
     * Détermine si le joueur peut actuellement survoler une pièce.
     * Le survol est autorisé uniquement si :
     * <ul>
     *   <li>Le jeu est initialisé</li>
     *   <li>Le joueur actuel est un humain</li>
     *   <li>Aucune pièce n’a encore été sélectionnée</li>
     * </ul>
     *
     * @param jeu L’instance du jeu.
     * @return true si le survol est autorisé, false sinon.
     */
    public static boolean peutSurvoler(Jeu jeu) {
        return jeu != null &&
               jeu.getJoueurActuel() != null &&
               jeu.getPieceCourante() == null &&
               JoueurControleur.estHumain(jeu.getJoueurActuel());
    }

    /**
     * Détermine quelles pièces doivent être supprimées du panneau graphique.
     * <p>
     * Cette méthode compare les pièces actuellement affichées à l'écran avec
     * les pièces restantes dans le modèle du jeu.
     * </p>
     *
     * @param panelsVisibles Liste des pièces actuellement affichées.
     * @param jeu             L’état du jeu.
     * @return La liste des pièces à supprimer.
     */
    public static List<Piece> calculerPiecesSupprimees(List<Piece> panelsVisibles, Jeu jeu) {
        Set<Piece> restantes = new HashSet<>(jeu.getPiecesRestantes());
        List<Piece> aSupprimer = new ArrayList<>();

        for (Piece piece : panelsVisibles) {
            if (!restantes.contains(piece)) {
                aSupprimer.add(piece);
            }
        }
        return aSupprimer;
    }
}