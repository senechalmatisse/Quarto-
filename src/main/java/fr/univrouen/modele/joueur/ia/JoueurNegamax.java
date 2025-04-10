package fr.univrouen.modele.joueur.ia;

import java.util.List;

import fr.univrouen.modele.arbre.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Implémente un joueur contrôlé par l’IA utilisant l’algorithme Négamax.
 * <p>
 * Cette IA cherche à maximiser sa propre évaluation en minimisant celle de l'adversaire
 * via l'inversion des scores à chaque niveau de l'arbre de jeu.
 * </p>
 * 
 * @see JoueurIA
 * @see ArborescenceNegamax
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class JoueurNegamax extends JoueurIA {

    /**
     * Constructeur du joueur IA utilisant l'algorithme Négamax.
     */
    public JoueurNegamax() {}

    @Override
    public String getNom() {
        return "Joueur Négamax";
    }

    @Override
    public void choisirPiece(Jeu jeu, Piece ignoree) {
        // Construction de l'arbre de recherche depuis la situation actuelle (Joueur MIN = celui qui donne une pièce)
        Arborescence constructeur = new ArborescenceNegamax();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MIN);

        NoeudArbre meilleurNoeud = null;                   // Meilleur nœud trouvé (meilleure pièce à donner)
        double meilleureValeur = Double.NEGATIVE_INFINITY; // Valeur d’heuristique maximale rencontrée

        // Parcours de tous les enfants de la racine (chaque enfant représente une pièce possible à donner)
        for (NoeudArbre fils = racine.getFilsaine(); fils != null; fils = fils.getFreredroit()) {
            Piece pieceTestee = fils.getPieceDonnee();
            double valeurHeuristique = fils.getHeuristique();

            // On évite de donner une pièce qui permettrait à l’adversaire de gagner immédiatement
            if (pieceTestee != null && piecePermetVictoireAdversaire(jeu, pieceTestee)) {
                continue; // On passe à la pièce suivante
            }

            // Sélectionne la pièce ayant la meilleure évaluation
            if (valeurHeuristique > meilleureValeur) {
                meilleureValeur = valeurHeuristique;
                meilleurNoeud = fils;
            }
        }

        // Si aucun meilleur nœud n’a été trouvé, on prend une pièce par défaut
        Piece pieceChoisie = (meilleurNoeud != null) 
            ? meilleurNoeud.getPieceDonnee() 
            : choisirPieceParDefaut(jeu);

        // Met à jour le jeu avec la pièce choisie, si elle existe
        if (pieceChoisie != null) {
            jeu.setPieceCourante(pieceChoisie); // On la donne à l’adversaire
        }

        jeu.tourSuivant();
    }

    /**
     * Place la pièce courante sur le plateau en recherchant d’abord une victoire immédiate,
     * puis en appliquant l’algorithme Négamax pour une décision stratégique.
     *
     * @param jeu       Le jeu en cours.
     * @param piece     La pièce à placer.
     * @param ignoree   (non utilisé ici)
     */
    @Override
    public void placerPiece(Jeu jeu, Piece piece, Position ignoree) {
        List<Position> positionsDisponibles = jeu.getPlateau().getPositionsDisponibles();

        // Étape 1 : vérifier s'il existe une position menant à une victoire immédiate
        Position positionGagnante = rechercherVictoireImmediate(jeu, piece, positionsDisponibles);
        if (positionGagnante != null) {
            appliquerPlacement(jeu, piece, positionGagnante);
            return;
        }

        // Étape 2 : sinon, explorer l’arbre Négamax pour trouver la meilleure position
        ArborescenceNegamax constructeur = new ArborescenceNegamax();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MAX);

        NoeudArbre meilleurFils = trouverFilsAvecMeilleureHeuristique(racine);
        Position positionChoisie = (meilleurFils != null && meilleurFils.getPositionJouee() != null)
            ? meilleurFils.getPositionJouee()
            : positionsDisponibles.get(0); // fallback si rien trouvé

            appliquerPlacement(jeu, piece, positionChoisie);
    }

    /**
     * Détecte s’il est possible de gagner immédiatement en plaçant la pièce.
     *
     * @param jeu         Le jeu courant.
     * @param piece       La pièce à tester.
     * @param positions   Toutes les positions disponibles sur le plateau.
     * @return La position gagnante si existante, sinon {@code null}.
     */
    private Position rechercherVictoireImmediate(Jeu jeu, Piece piece, List<Position> positions) {
        for (Position pos : positions) {
            Jeu copie = jeu.copier();
            copie.getPlateau().placerPiece(piece, pos);
            copie.retirerPieceChoisit(piece);
            copie.setPieceCourante(null);

            if (copie.aGagne() && !copie.getJoueurGagnant().getNom().contains("Humain")) {
                return pos;
            }
        }
        return null;
    }

    /**
     * Recherche parmi les fils de l’arbre celui qui possède la meilleure heuristique.
     *
     * @param racine La racine de l’arbre.
     * @return Le nœud fils le plus prometteur ou {@code null} si aucun.
     */
    private NoeudArbre trouverFilsAvecMeilleureHeuristique(NoeudArbre racine) {
        NoeudArbre meilleurFils = null;
        double meilleureValeur = Double.NEGATIVE_INFINITY;

        for (NoeudArbre fils = racine.getFilsaine(); fils != null; fils = fils.getFreredroit()) {
            double valeur = fils.getHeuristique();
            if (valeur > meilleureValeur) {
                meilleureValeur = valeur;
                meilleurFils = fils;
            }
        }

        return meilleurFils;
    }

    @Override
    public String toString() {
        return "C'est le tour du joueur Négamax";
    }
}