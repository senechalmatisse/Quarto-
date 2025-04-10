package fr.univrouen.modele.jeu.partie;

import java.util.List;

import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.*;
import fr.univrouen.modele.jeu.partie.observer.Observable;

/**
 * Interface représentant le contrat général pour une partie du jeu Quarto.
 * <p>
 * Elle définit les opérations essentielles que toute implémentation du jeu doit fournir :
 * gestion des tours, des joueurs, du plateau, des pièces, de la stratégie gagnante, et du cycle de vie de la partie.
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 2.1
 * @since JDK 17
 * @see Observable
 */
public interface Jeu extends Observable {

    /**
     * Passe au joueur suivant et déclenche la mise à jour des observateurs.
     */
    void tourSuivant();

    /**
     * Retourne le plateau de jeu courant.
     *
     * @return L'instance actuelle du plateau.
     */
    Plateau getPlateau();

    /**
     * Retourne la liste des pièces encore disponibles à jouer.
     *
     * @return Liste des pièces restantes.
     */
    List<Piece> getPiecesRestantes();

    /**
     * Retourne le joueur dont c'est actuellement le tour.
     *
     * @return Le joueur actif.
     */
    Joueur getJoueurActuel();

    /**
     * Retourne la pièce sélectionnée en cours de partie.
     *
     * @return La pièce courante à placer.
     */
    Piece getPieceCourante();

    /**
     * Définit la pièce qui devra être placée sur le plateau.
     *
     * @param nouvellePieceCourante La pièce choisie à affecter comme courante.
     */
    void setPieceCourante(Piece nouvellePieceCourante);

    /**
     * Vérifie si la partie vient tout juste de commencer.
     *
     * @return true si aucune pièce n’a encore été placée, false sinon.
     */
    boolean debutPartie();

    /**
     * Retire une pièce de la liste des pièces restantes après qu'elle ait été jouée.
     *
     * @param pieceChoisit La pièce qui a été utilisée.
     */
    void retirerPieceChoisit(Piece pieceChoisit);

    /**
     * Indique si la partie est terminée (victoire ou plus de pièces disponibles).
     *
     * @return true si la partie est finie, false sinon.
     */
    boolean estTerminee();

    /**
     * Retourne le joueur considéré comme gagnant, s’il y en a un.
     *
     * @return Le joueur gagnant, ou null s’il n’y a pas encore de gagnant.
     */
    Joueur estGagnant();

    /**
     * Crée une copie indépendante de l’état actuel du jeu.
     *
     * @return Une nouvelle instance représentant une copie exacte du jeu.
     */
    Jeu copier();

    /**
     * Retourne le joueur gagnant s’il y en a un.
     *
     * @return Le joueur gagnant, ou null s’il n’y a pas encore de gagnant.
     */
    Joueur getJoueurGagnant();

    /**
     * Vérifie s’il y a actuellement une condition de victoire sur le plateau.
     *
     * @return true si un alignement gagnant est détecté, false sinon.
     */
    boolean aGagne();
}