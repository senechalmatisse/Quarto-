package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Constructeur d'arbre basé sur l'algorithme Négamax pour le jeu Quarto.
 * Contrairement à Minimax, Négamax repose sur une seule fonction d'évaluation
 * où chaque joueur maximise son gain en inversant le score de son adversaire.
 * 
 * @see NoeudArbre
 * @see Heuristique
 * @see ArborescenceAbstrait
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class ArborescenceNegamax extends ArborescenceAbstrait {

    @Override
    public NoeudArbre construireArbre(Jeu jeu, Joueur joueur) {
        NoeudArbre racine = new NoeudArbre(joueur, jeu);
        double valeur = negamax(racine, PROFONDEUR_MAX, joueur);
        racine.setHeuristique(valeur);
        return racine;
    }

    @Override
    protected void construireFilsPlacement(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur, Piece pieceAPlacer) {
        NoeudArbre precedent = null;
        double valeurMax = Double.NEGATIVE_INFINITY;

        for (Position pos : jeu.getPlateau().getPositionsDisponibles()) {
            // Simulation du placement de la pièce sur une position donnée
            Jeu copie = simulerPlacement(jeu, pieceAPlacer, pos);

            // Création du nœud fils simulant ce coup
            NoeudArbre fils = new NoeudArbre(inverse(joueur), copie);
            fils.setPositionJouee(pos); // On note la position jouée pour pouvoir la retrouver

            // Appel récursif : on inverse le joueur et on explore le sous-arbre
            double val = -negamax(fils, profondeur - 1, inverse(joueur));
            fils.setHeuristique(val);            // On stocke l'évaluation du nœud
            valeurMax = Math.max(valeurMax, val);  // Mise à jour de la meilleure valeur trouvée

            // Chaînage dans l’arbre : on ajoute ce nœud enfant au parent
            precedent = ajouterFils(parent, precedent, fils);
        }
    }

    /**
     * Applique récursivement l'algorithme Négamax à partir du nœud donné.
     * Le principe repose sur l'évaluation symétrique des états : chaque joueur maximise
     * son gain en minimisant celui de son adversaire.
     *
     * @param noeud       Le nœud courant à analyser.
     * @param profondeur  Profondeur maximale restante d'exploration.
     * @param joueur      Le joueur qui joue ce tour (MAX ou MIN).
     * @return            La valeur négamaxée (évaluation optimale du nœud).
     */
    private double negamax(NoeudArbre noeud, int profondeur, Joueur joueur) {
        Jeu jeu = noeud.getEtatJeu();

        // Cas de base : si profondeur atteinte ou partie terminée, on évalue directement le nœud
        if (profondeur == 0 || jeu.estTerminee()) {
            return evaluerNegamax(jeu, joueur);  // Heuristique du point de vue du joueur courant
        }

        // Initialisation de la meilleure valeur possible pour ce nœud (on maximise)
        double valeurMax = Double.NEGATIVE_INFINITY;
        NoeudArbre precedent = null;

        // Cas 1 : phase de placement (une pièce a été donnée, il faut la placer)
        Piece pieceAPlacer = jeu.getPieceCourante();
        if (pieceAPlacer != null) {
            construireFilsPlacement(noeud, jeu, joueur, profondeur, pieceAPlacer);
        } else {
            // Cas 2 : phase de choix de pièce (on doit donner une pièce à l’adversaire)
            for (Piece piece : jeu.getPiecesRestantes()) {
                // Simulation du choix de cette pièce
                Jeu copie = simulerChoix(jeu, piece);

                // Création du nœud enfant simulant ce choix
                NoeudArbre enfant = new NoeudArbre(inverse(joueur), copie);
                enfant.setPieceDonnee(piece);

                // Appel récursif : on passe au tour suivant avec l'adversaire
                double val = -negamax(enfant, profondeur - 1, inverse(joueur));
                enfant.setHeuristique(val);
                valeurMax = Math.max(valeurMax, val);  // Mise à jour du meilleur score possible

                // Ajout du nœud enfant à l’arbre
                precedent = ajouterFils(noeud, precedent, enfant);
            }
        }

        return valeurMax;  // On retourne la meilleure évaluation trouvée parmi tous les enfants
    }

    /**
     * Évalue un état de jeu selon l’heuristique négamax :
     * l’évaluation est positive si favorable au joueur courant, négative sinon.
     *
     * @param jeu    L’état du jeu à évaluer.
     * @param joueur Le joueur actif (MAX ou MIN).
     * @return Une valeur réelle représentant l’évaluation de l’état.
     */
    private double evaluerNegamax(Jeu jeu, Joueur joueur) {
        if (jeu.aGagne()) {
            boolean estMinimax = jeu.getJoueurGagnant() != null && 
                                 jeu.getJoueurGagnant().getNom().contains("Minimax");
            boolean estJoueurCourant = (joueur == Joueur.MAX);
            return (estMinimax == estJoueurCourant) ? GAIN_MAX : GAIN_MIN;
        }

        // Heuristique basée sur les alignements potentiels
        double h = Heuristique.evaluerAlignements(jeu.getPlateau().getAlignementsPotentiels());

        // Négamax : retourne h si joueur MAX, -h si joueur MIN
        return (joueur == Joueur.MAX) ? h : -h;
    }
}