package fr.univrouen.modele.joueur.ia;

import fr.univrouen.modele.arbre.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.Position;

import java.util.List;

/**
 * Représente un joueur IA utilisant l'algorithme Alpha-Bêta.
 * <p>
 * Ce joueur simule les coups à venir à l'aide d'un arbre de jeu,
 * et applique un élagage des branches non optimales.
 * Il choisit une pièce à donner à l'adversaire (phase de choix)
 * ou une position où placer une pièce (phase de placement).
 * </p>
 * 
 * @see JoueurIA
 * @see ArborescenceAlphaBeta
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class JoueurAlphaBeta extends JoueurIA {

    /**
     * Constructeur du joueur IA utilisant l'algorithme Alpha-Bêta.
     */
    public JoueurAlphaBeta() {}

    @Override
    public String getNom() {
        return "Joueur Alpha-Bêta";
    }

    /**
     * Phase de **choix de pièce** : l'IA cherche à donner la pièce
     * la moins favorable à l'adversaire.
     *
     * @param jeu     Le jeu actuel.
     * @param ignoree Ignoré dans ce contexte (ancienne pièce éventuellement donnée).
     */
    @Override
    public void choisirPiece(Jeu jeu, Piece ignoree) {
        Arborescence constructeur = new ArborescenceAlphaBeta();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MIN);

        NoeudArbre meilleurChoix = null;
        double meilleureHeuristique = Double.NEGATIVE_INFINITY;

        // Parcours des enfants (fils) du nœud racine
        for (NoeudArbre enfant = racine.getFilsaine(); enfant != null; enfant = enfant.getFreredroit()) {
            Piece pieceCandidate = enfant.getPieceDonnee();
            double heuristique = enfant.getHeuristique();

            // On évite de donner une pièce qui mènerait à une victoire immédiate de l’adversaire
            if (pieceCandidate != null && piecePermetVictoireAdversaire(jeu, pieceCandidate)) {
                continue;
            }

            if (heuristique > meilleureHeuristique) {
                meilleureHeuristique = heuristique;
                meilleurChoix = enfant;
            }
        }

        // Si aucun bon choix n'a été trouvé, on en prend une par défaut
        Piece pieceFinale = (meilleurChoix != null)
            ? meilleurChoix.getPieceDonnee()
            : choisirPieceParDefaut(jeu);

        if (pieceFinale != null) {
            jeu.setPieceCourante(pieceFinale);
        }

        jeu.tourSuivant();
    }

    /**
     * Phase de **placement de pièce** : l'IA choisit la meilleure position où placer la pièce.
     *
     * @param jeu      Le jeu actuel.
     * @param piece    La pièce à placer.
     * @param ignoree  Non utilisé ici (position suggérée).
     */
    @Override
    public void placerPiece(Jeu jeu, Piece piece, Position ignoree) {
        List<Position> positionsDisponibles = jeu.getPlateau().getPositionsDisponibles();

        // 1. Vérifie s'il existe une position menant à une victoire immédiate
        for (Position position : positionsDisponibles) {
            Jeu simulation = jeu.copier();
            simulation.getPlateau().placerPiece(piece, position);
            simulation.retirerPieceChoisit(piece);
            simulation.setPieceCourante(null);

            if (simulation.aGagne() && !simulation.getJoueurGagnant().getNom().contains("Humain")) {
                appliquerPlacement(jeu, piece, position);
                return;
            }
        }

        // 2. Sinon, utilise l’arborescence Alpha-Bêta pour choisir la meilleure position
        Arborescence constructeur = new ArborescenceAlphaBeta();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MAX);

        NoeudArbre meilleur = trouverMeilleurFils(racine);
        Position positionChoisie = (meilleur != null)
                ? meilleur.getPositionJouee()
                : positionsDisponibles.get(0);

        appliquerPlacement(jeu, piece, positionChoisie);
    }

    /**
     * Trouve le nœud fils ayant la meilleure heuristique.
     *
     * @param racine Le nœud racine analysé.
     * @return Le meilleur nœud parmi ses enfants.
     */
    private NoeudArbre trouverMeilleurFils(NoeudArbre racine) {
        NoeudArbre meilleur = null;
        double maxHeuristique = Double.NEGATIVE_INFINITY;

        for (NoeudArbre fils = racine.getFilsaine(); fils != null; fils = fils.getFreredroit()) {
            double h = fils.getHeuristique();
            if (h > maxHeuristique) {
                meilleur = fils;
                maxHeuristique = h;
            }
        }

        return meilleur;
    }

    @Override
    public String toString() {
        return "C'est le tour du joueur Alpha-Bêta";
    }
}