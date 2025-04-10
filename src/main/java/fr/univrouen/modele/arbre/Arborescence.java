package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.partie.Jeu;

/**
 * Interface représentant un constructeur d’arbre pour les algorithmes de décision (Minimax, Négamax).
 */
public interface Arborescence {

    /**
     * Construit un arbre de décision à partir d’un état de jeu et d’un joueur donné.
     *
     * @param jeu    État courant du jeu.
     * @param joueur Joueur actif à la racine.
     * @see NoeudArbre
     * 
     * @return Racine de l’arbre construit.
     */
    NoeudArbre construireArbre(Jeu jeu, Joueur joueur);
}