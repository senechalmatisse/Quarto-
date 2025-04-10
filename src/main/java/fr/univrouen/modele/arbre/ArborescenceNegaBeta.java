package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.piece.*;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Implémentation de l'algorithme NegaBeta pour générer un arbre de décision dans le jeu Quarto.
 * <p>
 * Cette classe hérite de {@link ArborescenceAbstrait} et applique une variante de l'algorithme Negamax,
 * combinée avec l'élagage alpha-bêta, pour évaluer les coups à jouer. Elle construit récursivement 
 * l'arbre de jeu à une profondeur fixe et attribue des scores heuristiques aux noeuds pour guider l'IA.
 * </p>
 *
 * @see NoeudArbre
 * @see Heuristique
 * @see ArborescenceAbstrait
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class ArborescenceNegaBeta extends ArborescenceAbstrait {

    @Override
    public NoeudArbre construireArbre(Jeu jeu, Joueur joueur) {
        NoeudArbre racine = new NoeudArbre(joueur, jeu);
        double valeur = alphaBetaNegamax(racine, PROFONDEUR_MAX, joueur, GAIN_MIN, GAIN_MAX);
        racine.setHeuristique(valeur);
        return racine;
    }

    private double alphaBetaNegamax(NoeudArbre noeud, int profondeur, Joueur joueur, double alpha, double beta) {
        Jeu jeu = noeud.getEtatJeu();

        if (profondeur == 0 || jeu.estTerminee()) {
            return evaluerNegamax(jeu, joueur);
        }

        double valeur = GAIN_MIN;
        NoeudArbre precedent = null;

        Piece pieceAPlacer = jeu.getPieceCourante();
        if (pieceAPlacer != null) {
            for (Position pos : jeu.getPlateau().getPositionsDisponibles()) {
                Jeu copie = simulerPlacement(jeu, pieceAPlacer, pos);
                NoeudArbre enfant = new NoeudArbre(inverse(joueur), copie);
                enfant.setPositionJouee(pos);

                double score = -alphaBetaNegamax(enfant, profondeur - 1, inverse(joueur), -beta, -alpha);
                enfant.setHeuristique(score);
                valeur = Math.max(valeur, score);
                alpha = Math.max(alpha, score);

                precedent = ajouterFils(noeud, precedent, enfant);
                if (alpha >= beta) break; // Élagage
            }
        } else {
            for (Piece piece : jeu.getPiecesRestantes()) {
                Jeu copie = simulerChoix(jeu, piece);
                NoeudArbre enfant = new NoeudArbre(inverse(joueur), copie);
                enfant.setPieceDonnee(piece);

                double score = -alphaBetaNegamax(enfant, profondeur - 1, inverse(joueur), -beta, -alpha);
                enfant.setHeuristique(score);
                valeur = Math.max(valeur, score);
                alpha = Math.max(alpha, score);

                precedent = ajouterFils(noeud, precedent, enfant);
                if (alpha >= beta) break; // Élagage
            }
        }

        return valeur;
    }

    private double evaluerNegamax(Jeu jeu, Joueur joueur) {
        if (jeu.aGagne()) {
            boolean estMinimax = jeu.getJoueurGagnant() != null && jeu.getJoueurGagnant().getNom().contains("Minimax");
            boolean estJoueurCourant = (joueur == Joueur.MAX);
            return (estMinimax == estJoueurCourant) ? GAIN_MAX : GAIN_MIN;
        }

        double h = Heuristique.evaluerAlignements(jeu.getPlateau().getAlignementsPotentiels());
        return (joueur == Joueur.MAX) ? h : -h;
    }

    @Override
    protected void construireFilsPlacement(NoeudArbre parent, Jeu jeu, Joueur joueur, int profondeur, Piece pieceAPlacer) {
        throw new UnsupportedOperationException("Méthode pas implémentée");
    }
}
