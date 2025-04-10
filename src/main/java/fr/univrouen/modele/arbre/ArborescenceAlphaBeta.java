package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.piece.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Constructeur d'arbre utilisant l'algorithme Alpha-Bêta.
 * Cet algorithme améliore Minimax en évitant d’explorer des branches inutiles
 * lorsque l’on sait qu’elles ne peuvent pas influencer le résultat final.
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class ArborescenceAlphaBeta extends ArborescenceAbstrait {

    @Override
    public NoeudArbre construireArbre(Jeu jeu, Joueur joueur) {
        NoeudArbre racine = new NoeudArbre(joueur, jeu);
        double heuristique = alphaBeta(racine, PROFONDEUR_MAX, GAIN_MIN, GAIN_MAX, joueur);
        racine.setHeuristique(heuristique);
        return racine;
    }

    /**
     * Méthode principale de l'algorithme Alpha-Bêta avec approche Négamax.
     * Chaque appel inverse les rôles et applique la recherche en profondeur
     * avec élagage.
     *
     * @param noeud      Le nœud courant.
     * @param profondeur Profondeur maximale restante.
     * @param alpha      Valeur alpha (borne inférieure).
     * @param beta       Valeur beta (borne supérieure).
     * @param joueur     Joueur actuel (MAX ou MIN).
     * @return La meilleure valeur trouvée pour ce nœud.
     */
    private double alphaBeta(NoeudArbre noeud, int profondeur, double alpha, double beta, Joueur joueur) {
        Jeu jeu = noeud.getEtatJeu();

        // Cas terminal : on retourne la valeur d’évaluation
        if (profondeur == 0 || jeu.estTerminee()) {
            return evaluerEtatTerminal(jeu, joueur);
        }

        NoeudArbre precedent = null;
        double valeurMax = Double.NEGATIVE_INFINITY;

        Piece pieceAPlacer = jeu.getPieceCourante();

        // Phase de placement : on teste toutes les positions disponibles
        if (pieceAPlacer != null) {
            for (Position position : jeu.getPlateau().getPositionsDisponibles()) {
                Jeu copie = simulerPlacement(jeu, pieceAPlacer, position);

                NoeudArbre enfant = new NoeudArbre(inverse(joueur), copie);
                enfant.setPositionJouee(position);

                double valeur = -alphaBeta(enfant, profondeur - 1, -beta, -alpha, inverse(joueur));
                enfant.setHeuristique(valeur);
                valeurMax = Math.max(valeurMax, valeur);
                alpha = Math.max(alpha, valeur);

                precedent = ajouterFils(noeud, precedent, enfant);

                // Élagage : inutile de continuer si alpha >= beta
                if (alpha >= beta) break;
            }

            return alpha;
        }

        // Phase de choix de pièce : on teste toutes les pièces restantes
        for (Piece piece : jeu.getPiecesRestantes()) {
            Jeu copie = simulerChoix(jeu, piece);

            NoeudArbre enfant = new NoeudArbre(inverse(joueur), copie);
            enfant.setPieceDonnee(piece);

            double valeur = -alphaBeta(enfant, profondeur - 1, -beta, -alpha, inverse(joueur));
            enfant.setHeuristique(valeur);
            valeurMax = Math.max(valeurMax, valeur);
            alpha = Math.max(alpha, valeur);

            precedent = ajouterFils(noeud, precedent, enfant);

            if (alpha >= beta) break;
        }

        return alpha;
    }

    @Override
    protected void construireFilsPlacement(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur, Piece pieceAPlacer) {
        throw new UnsupportedOperationException("Méthode pas implémentée");
    }
}