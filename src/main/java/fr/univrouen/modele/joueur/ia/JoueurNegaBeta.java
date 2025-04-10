package fr.univrouen.modele.joueur.ia;

import java.util.List;

import fr.univrouen.modele.arbre.*;
import fr.univrouen.modele.jeu.partie.*;
import fr.univrouen.modele.jeu.partie.plateau.Position;
import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Implémentation d'un joueur IA utilisant l'algorithme Néga-Bêta.
 * <p>
 * Ce joueur choisit les pièces à donner à l'adversaire et les positions
 * où jouer en utilisant une stratégie fondée sur l'arborescence des coups
 * possibles, évaluée par l’algorithme NegaBeta.
 * </p>
 * 
 * @see JoueurIA
 * @see ArborescenceNegaBeta
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class JoueurNegaBeta extends JoueurIA {

    /**
     * Constructeur du joueur IA utilisant l'algorithme Alpha-Bêta avec les valeurs Négamax.
     */
    public JoueurNegaBeta() {}

    @Override
    public String getNom() {
        return "Joueur Néga-Bêta";
    }

    @Override
    public void choisirPiece(Jeu jeu, Piece ignored) {
        // Création de l'arbre de décision selon l'algorithme NegaBeta, en simulant le choix MIN
        Arborescence constructeur = new ArborescenceNegaBeta();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MIN);

        NoeudArbre meilleur = null;
        double max = Double.NEGATIVE_INFINITY;

        // Parcours de tous les enfants (pièces à donner possibles)
        for (NoeudArbre enfant = racine.getFilsaine(); enfant != null; enfant = enfant.getFreredroit()) {
            double val = enfant.getHeuristique();          // Valeur heuristique de cette option
            Piece piece = enfant.getPieceDonnee();         // Pièce que l’on envisage de donner

            // Si la pièce ne permet pas une victoire immédiate de l’adversaire, et qu’elle est meilleure, on la garde
            if (piece != null && !piecePermetVictoireAdversaire(jeu, piece) && val > max) {
                meilleur = enfant;
                max = val;
            }
        }

        // Si aucun bon choix n'a été trouvé, on en prend une par défaut
        Piece pieceFinale = (meilleur != null)
            ? meilleur.getPieceDonnee()
            : choisirPieceParDefaut(jeu);

        // Attribution de la pièce choisie au jeu
        if (pieceFinale != null) {
            jeu.setPieceCourante(pieceFinale);
        }

        jeu.tourSuivant();
    }

    @Override
    public void placerPiece(Jeu jeu, Piece piece, Position ignored) {
        List<Position> positionsDisponibles = jeu.getPlateau().getPositionsDisponibles();

        // 1. Vérification d'une victoire immédiate possible
        Position positionGagnante = chercherPlacementGagnantImmediat(jeu, piece, positionsDisponibles);
        if (positionGagnante != null) {
            appliquerPlacement(jeu, piece, positionGagnante);
            return;
        }

        // 2. Recherche d'un placement optimal via l'algorithme NegaBeta
        Position meilleurePosition = chercherMeilleurPlacementOptimal(jeu, positionsDisponibles);
        appliquerPlacement(jeu, piece, meilleurePosition);
    }

    /**
     * Recherche une position qui permet à l'IA de gagner immédiatement.
     *
     * @param jeu Le jeu à simuler.
     * @param piece La pièce à tester.
     * @param positionsDisponibles Toutes les cases libres du plateau.
     * @return La position menant à une victoire immédiate, ou null si aucune trouvée.
     */
    private Position chercherPlacementGagnantImmediat(Jeu jeu, Piece piece, List<Position> positionsDisponibles) {
        for (Position position : positionsDisponibles) {
            Jeu simulation = jeu.copier();
            simulation.getPlateau().placerPiece(piece, position);
            simulation.retirerPieceChoisit(piece);
            simulation.setPieceCourante(null);

            // On vérifie si cette simulation entraîne une victoire pour l’IA
            boolean victoireIA = simulation.aGagne() &&
                                 !simulation.getJoueurGagnant().getNom().contains("Humain");

            // Si oui, on retourne la position gagnante immédiatement
            if (victoireIA) {
                return position;
            }
        }

        // Sinon, on retourne null
        return null;
    }

    /**
     * Applique l'algorithme NegaBeta pour rechercher le meilleur placement stratégique.
     *
     * @param jeu L'état du jeu actuel.
     * @param positionsDisponibles Toutes les cases libres du plateau.
     * @return La meilleure position trouvée, ou une position par défaut si aucune optimale trouvée.
     */
    private Position chercherMeilleurPlacementOptimal(Jeu jeu, List<Position> positionsDisponibles) {
        Arborescence constructeur = new ArborescenceNegaBeta();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MAX);

        NoeudArbre meilleurNoeud = null;
        double meilleureHeuristique = Double.NEGATIVE_INFINITY;

        // Parcours des enfants du nœud racine (chaque enfant représente un coup possible)
        for (NoeudArbre enfant = racine.getFilsaine(); enfant != null; enfant = enfant.getFreredroit()) {
            double heuristique = enfant.getHeuristique();

            // On garde le nœud avec la meilleure évaluation (la plus haute)
            if (heuristique > meilleureHeuristique) {
                meilleureHeuristique = heuristique;
                meilleurNoeud = enfant;
            }
        }

        return (meilleurNoeud != null)
            ? meilleurNoeud.getPositionJouee()  // Si un bon coup a été trouvé, on retourne sa position.
            : positionsDisponibles.get(0);      // Sinon, on joue la première position disponible.
    }    

    @Override
    public String toString() {
        return "C'est le tour du joueur Néga-Bêta";
    }
}