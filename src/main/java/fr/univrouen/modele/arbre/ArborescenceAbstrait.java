package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.Position;
import fr.univrouen.modele.jeu.piece.Heuristique;
import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe abstraite définissant les éléments communs aux algorithmes d'exploration
 * d’arborescence comme Minimax ou Négamax dans le cadre du jeu Quarto.
 * 
 * <p>Elle encapsule les utilitaires partagés : simulation d’actions (placement, choix),
 * inversion de joueur, gestion de la structure de l’arbre via des liens de type
 * fils aîné / frère droit.</p>
 * 
 * @see NoeudArbre
 * @see Heuristique
 * @see Arborescence
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public abstract class ArborescenceAbstrait implements Arborescence {
    /** Profondeur maximale d'exploration de l'arbre. */
    protected static final int PROFONDEUR_MAX = 2;

    /** Valeur maximale de gain (victoire assurée pour Minimax). */
    protected static final double GAIN_MAX = Double.POSITIVE_INFINITY;

    /** Valeur minimale de gain (défaite assurée pour Minimax). */
    protected static final double GAIN_MIN = Double.NEGATIVE_INFINITY;

    /**
     * Ajoute un nœud enfant à un nœud parent selon la structure 
     * de fils aîné et frère droit (liste chaînée horizontale).
     *
     * @param parent     Le nœud parent dans l’arbre.
     * @param precedent  Le nœud frère précédemment ajouté (ou {@code null} si c’est le premier).
     * @param fils       Le nœud fils à ajouter.
     * @return Le nœud fils, pour permettre le chaînage dans les appels successifs.
     */
    protected NoeudArbre ajouterFils(NoeudArbre parent, NoeudArbre precedent, NoeudArbre fils) {
        if (precedent == null) {
            parent.setFilsaine(fils);
        } else {
            precedent.setFreredroit(fils);
        }
        return fils;
    }

    /**
     * Simule le placement d’une pièce sur le plateau en créant une copie du jeu,
     * utilisée dans l’arborescence pour prédire les coups futurs.
     *
     * @param jeu    L’état courant du jeu à copier.
     * @param piece  La pièce à placer.
     * @param pos    La position de placement.
     * @return Un nouvel état du jeu après le placement simulé.
     */
    protected Jeu simulerPlacement(Jeu jeu, Piece piece, Position pos) {
        Jeu copie = jeu.copier();
        copie.getPlateau().placerPiece(piece, pos);         // Placement simulé
        copie.retirerPieceChoisit(piece);                   // Retrait de la pièce de la réserve
        copie.setPieceCourante(null);
        copie.tourSuivant();
        return copie;
    }

    /**
     * Simule un choix de pièce dans une copie du jeu.
     * Utile pour la phase où un joueur choisit la pièce à donner à l’adversaire.
     *
     * @param jeu    L’état courant du jeu à copier.
     * @param piece  La pièce que l’on souhaite tester comme choix.
     * @return Une nouvelle configuration du jeu après le choix simulé.
     */
    protected Jeu simulerChoix(Jeu jeu, Piece piece) {
        Jeu copie = jeu.copier();
        copie.setPieceCourante(piece);
        copie.tourSuivant();
        return copie;
    }

    /**
     * Inverse le rôle du joueur courant.
     * Si le joueur est MAX, retourne MIN ; sinon retourne MAX.
     *
     * @param joueur Le joueur courant.
     * @return Le joueur opposé.
     */
    protected Joueur inverse(Joueur joueur) {
        return joueur == Joueur.MAX ? Joueur.MIN : Joueur.MAX;
    }

    /**
     * Évalue la valeur heuristique d’un état terminal du jeu (fin de partie ou profondeur maximale atteinte).
     * <p>
     * Cette méthode est utilisée lorsque :
     * <ul>
     *   <li>Le jeu est terminé (victoire/défaite).</li>
     *   <li>La profondeur maximale de l’arbre a été atteinte (feuille dans l’arbre Minimax).</li>
     * </ul>
     * Elle retourne une heuristique correspondant à une victoire, défaite ou évaluation pondérée si la partie continue.
     *
     * @param jeu     L’état actuel du jeu.
     * @param joueur  Le joueur en cours dans l’algorithme (MAX ou MIN), pour savoir s’il gagne ou perd.
     * @return Une valeur heuristique : {@code GAIN_MAX}, {@code GAIN_MIN}, ou une évaluation intermédiaire.
     */
    protected double evaluerEtatTerminal(Jeu jeu, Joueur joueur) {
        // Cas 1 : la partie est terminée (quelqu’un a gagné)
        if (jeu.aGagne()) {
            fr.univrouen.modele.joueur.Joueur gagnant = jeu.getJoueurGagnant();

            // Sécurité : s’il n’y a pas de gagnant détecté (cas anormal), on retourne 0
            if (gagnant == null) return 0;

            boolean estIa = !gagnant.getNom().contains("Humain");

            // Si le joueur courant dans l’algo (MAX) est Minimax ET c’est lui qui a gagné → GAIN_MAX
            // Sinon (l’autre joueur a gagné) → GAIN_MIN
            return (estIa == (joueur == Joueur.MAX)) ? GAIN_MAX : GAIN_MIN;
        }        

        // Cas 2 : la partie n’est pas finie mais on a atteint la profondeur limite → on retourne une évaluation pondérée
        return Heuristique.evaluerAlignements(jeu.getPlateau().getAlignementsPotentiels());
    }

    /**
     * Construit les enfants correspondant aux différentes positions où la pièce courante
     * peut être placée sur le plateau.
     *
     * @param parent         Le nœud parent à enrichir.
     * @param jeu            L'état du jeu actuel.
     * @param joueur         Le joueur qui joue ce tour.
     * @param profondeur     La profondeur actuelle dans l’arbre.
     * @param pieceAPlacer   La pièce à placer.
     */
    protected abstract void construireFilsPlacement(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur, Piece pieceAPlacer);
}