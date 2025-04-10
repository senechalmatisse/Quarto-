package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Constructeur d'arbre Minimax pour le jeu Quarto.
 * <p>
 * Cette classe construit récursivement une arborescence représentant
 * les différents états possibles du jeu, utilisée par les algorithmes
 * de décision comme Minimax.
 * </p>
 * <p>
 * Elle prend en compte les placements de pièces sur le plateau ainsi que
 * les choix de pièces à donner à l'adversaire.
 * </p>
 * 
 * @see NoeudArbre
 * @see Heuristique
 * @see ArborescenceAbstrait
 * @author Matisse SENECHAL
 * @version 1.2
 * @since JDK 17
 */
public class ArborescenceMinimax extends ArborescenceAbstrait {

    /**
     * Construit une instance de constructeur d'arbre avec la profondeur définie par défaut.
     */
    public ArborescenceMinimax() {}

    @Override
    public NoeudArbre construireArbre(Jeu jeu, Joueur joueur) {
        return construireRecursivement(jeu, joueur, 0);
    }

    /**
     * Méthode récursive principale pour explorer l’arbre jusqu’à une profondeur maximale.
     *
     * @param jeu         L’état courant du jeu.
     * @param joueur      Le joueur courant (MAX ou MIN).
     * @param profondeur  La profondeur actuelle dans l’arbre.
     * @return Le nœud correspondant à l'état évalué.
     */
    private NoeudArbre construireRecursivement(Jeu jeu, Joueur joueur, int profondeur) {
        NoeudArbre noeud = new NoeudArbre(joueur, jeu);

        if (profondeur == PROFONDEUR_MAX || jeu.estTerminee()) {
            noeud.setHeuristique(evaluerEtatTerminal(jeu, joueur));
            return noeud;
        }

        Piece pieceAPlacer = jeu.getPieceCourante();
        if (pieceAPlacer != null) {
            construireFilsPlacement(noeud, jeu, joueur, profondeur, pieceAPlacer);
        } else {
            construireFilsChoixPiece(noeud, jeu, joueur, profondeur);
        }

        propagerValeurMinimax(noeud, joueur);
        return noeud;
    }

    @Override
    protected void construireFilsPlacement(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur, Piece pieceAPlacer) {
        NoeudArbre precedent = null;

        // Parcours de toutes les positions libres sur le plateau
        for (Position pos : jeu.getPlateau().getPositionsDisponibles()) {    
            Jeu copie = simulerPlacement(jeu, pieceAPlacer, pos);

            // Création d’un nœud pour représenter cet état simulé
            NoeudArbre fils = new NoeudArbre(inverse(joueur), copie);
            fils.setPositionJouee(pos); // On note la position jouée pour pouvoir la retrouver

            // Si ce placement entraîne une victoire, on évalue immédiatement ce nœud comme terminal
            if (copie.aGagne()) {
                fr.univrouen.modele.joueur.Joueur gagnant = copie.getJoueurGagnant();

                // Heuristique maximale si la victoire est pour l’IA, minimale sinon
                if (gagnant != null && gagnant.getNom().contains("Minimax")) {
                    fils.setHeuristique(GAIN_MAX);
                } else {
                    fils.setHeuristique(GAIN_MIN);
                }
            } else {
                // Sinon, on continue l’exploration récursive en descendant dans l’arbre
                fils = construireRecursivement(copie, inverse(joueur), profondeur + 1);
            }

            // Ajout du nœud fils à la liste des enfants du parent, tout en chaînant les frères
            precedent = ajouterFils(parent, precedent, fils);
        }
    }

    /**
     * Construit les nœuds enfants représentant tous les choix possibles de pièces à donner à l'adversaire.
     * <p>
     * Cette méthode est appelée lorsqu'il n'y a pas de pièce courante à placer (c'est-à-dire qu'on est en
     * phase de **choix de pièce** et non de placement). Elle parcourt toutes les pièces restantes,
     * les simule comme pièce courante à donner à l'adversaire, puis explore récursivement les états futurs.
     * </p>
     *
     * @param parent      Le nœud parent actuel dans l’arborescence.
     * @param jeu         L’état du jeu à partir duquel générer les nœuds.
     * @param joueur      Le joueur actuel (MAX ou MIN).
     * @param profondeur  La profondeur actuelle dans l'arbre Minimax.
     */
    private void construireFilsChoixPiece(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur) {
        NoeudArbre precedent = null; // Sert à chaîner les frères droits

        // On teste chaque pièce restante que le joueur pourrait donner à son adversaire
        for (Piece piece : jeu.getPiecesRestantes()) {
            Jeu copie = simulerChoix(jeu, piece);

            // Création d’un nœud représentant ce choix de pièce
            NoeudArbre fils = new NoeudArbre(inverse(joueur), copie);
            fils.setPieceDonnee(piece); // On note la pièce donnée pour référence

            // Si ce choix mène à une défaite immédiate (le joueur adverse peut gagner), on évalue l'état
            if (copie.aGagne()) {
                // Le joueur MIN donne une pièce qui fait perdre MAX → heuristique max (danger)
                // Inversement, si MAX piège MIN → heuristique min
                fils.setHeuristique(joueur == Joueur.MIN ? GAIN_MAX : GAIN_MIN);

                // Comme une victoire/défaite immédiate a été trouvée, on peut s'arrêter
                parent.setFilsaine(fils);
                return;
            }

            // Sinon, on continue l'exploration récursive plus en profondeur
            fils = construireRecursivement(copie, inverse(joueur), profondeur + 1);
            fils.setPieceDonnee(piece); // Réaffecter car la récursion retourne un nouveau noeud

            precedent = ajouterFils(parent, precedent, fils);
        }
    }

    /**
     * Applique la logique Minimax pour propager la meilleure valeur heuristique parmi les enfants vers le nœud actuel.
     * <p>
     * Cette méthode est utilisée après avoir construit tous les enfants d’un nœud pour lui attribuer sa propre 
     * valeur heuristique. Elle suit le principe du Minimax :
     * <ul>
     *   <li>Si c’est au joueur <b>MAX</b> de jouer, on cherche le <b>meilleur coup</b> pour lui : on prend la <b>valeur maximale</b> parmi ses enfants.</li>
     *   <li>Si c’est au joueur <b>MIN</b> de jouer, on cherche le <b>pire coup</b> pour MAX (donc le meilleur pour MIN) : on prend la <b>valeur minimale</b> parmi ses enfants.</li>
     * </ul>
     * Cette valeur sera ensuite utilisée par les couches supérieures de l’arbre pour prendre la meilleure décision.
     * </p>
     * 
     * @param noeud   Le nœud courant dont on veut calculer la valeur heuristique à partir de ses enfants.
     * @param joueur  Le joueur associé à ce nœud (MAX ou MIN), qui décide comment évaluer les enfants.
     */
    private void propagerValeurMinimax(NoeudArbre noeud, Joueur joueur) {
        // Initialisation de la valeur heuristique selon le joueur courant :
        // - MAX commence avec le plus petit possible (on veut maximiser ensuite)
        // - MIN commence avec le plus grand possible (on veut minimiser ensuite)
        double valeur = (joueur == Joueur.MAX) ? GAIN_MIN : GAIN_MAX;

        // Parcours de tous les enfants du noeud (fils aîné puis les frères à droite)
        for (NoeudArbre enfant = noeud.getFilsaine(); enfant != null; enfant = enfant.getFreredroit()) {
            double h = enfant.getHeuristique();

            // On applique le choix optimal selon le joueur :
            // - MAX veut le max de ses enfants
            // - MIN veut le min de ses enfants
            valeur = (joueur == Joueur.MAX) ? Math.max(valeur, h) : Math.min(valeur, h);
        }

        // On affecte la meilleure (ou pire) valeur trouvée au noeud courant
        noeud.setHeuristique(valeur);
    }
}