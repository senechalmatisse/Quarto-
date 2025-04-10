package fr.univrouen.modele.joueur.ia;

/**
 * Énumération représentant les types d'intelligences artificielles disponibles dans le jeu Quarto.
 * <p>
 * Chaque valeur correspond à une stratégie d'IA utilisée pour déterminer les coups
 * à jouer lors d'une partie.
 * </p>
 *
 * <ul>
 *     <li>{@link #MINIMAX} : Algorithme classique de décision basé sur l’arbre Minimax.</li>
 *     <li>{@link #ALPHA_BETA} : Variante du Minimax avec élagage alpha-bêta pour améliorer l'efficacité.</li>
 *     <li>{@link #NEGAMAX} : Simplification de Minimax pour les jeux à somme nulle, avec une logique symétrique.</li>
 *     <li>{@link #NEGA_BETA} : Amélioration du Negamax avec élagage alpha-bêta (aussi appelé Principal Variation Search).</li>
 * </ul>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public enum IA {
    /** IA utilisant l’algorithme Minimax simple. */
    MINIMAX,

    /** IA utilisant l’algorithme Minimax avec élagage alpha-bêta. */
    ALPHA_BETA,

    /** IA utilisant l’algorithme Negamax. */
    NEGAMAX,

    /** IA utilisant Negamax avec élagage alpha-bêta (Nega-Beta). */
    NEGA_BETA
}
