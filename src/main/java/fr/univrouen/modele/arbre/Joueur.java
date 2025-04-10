package fr.univrouen.modele.arbre;

/**
 * Enumération représentant les deux types de joueurs utilisés dans l'algorithme Minimax.
 * <p>
 * Elle sert à identifier le rôle d'un nœud dans l'arbre de recherche :
 * <ul>
 *     <li><strong>MAX</strong> : représente l'IA (ou le joueur stratégique) qui cherche à maximiser la valeur heuristique.</li>
 *     <li><strong>MIN</strong> : représente l'adversaire (généralement l'humain) qui cherche à minimiser l’évaluation du MAX.</li>
 * </ul>
 *
 * Cette distinction permet de gérer les tours de jeu en simulant des décisions opposées dans l’arbre Minimax.
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public enum Joueur {
    /**
     * Le joueur MIN cherche à minimiser la valeur de l’heuristique (souvent l’adversaire de l’IA).
     */
    MIN,

    /**
     * Le joueur MAX cherche à maximiser la valeur de l’heuristique (souvent l’IA).
     */
    MAX
}