package fr.univrouen.modele.joueur.ia;

import java.util.*;

import fr.univrouen.modele.arbre.*;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.*;

/**
 * Implémentation d'un joueur IA utilisant l'algorithme Minimax.
 * Cette IA choisit une pièce à donner à l'adversaire et place une pièce sur le plateau
 * en fonction de l'analyse heuristique de l'arbre de jeu.
 * 
 * @see JoueurIA
 * @see ArborescenceMinimax
 * @author Matisse SENECHAL
 * @version 1.2
 * @since JDK 17
 */
public class JoueurMinimax extends JoueurIA {

    /**
     * Constructeur du joueur IA utilisant l'algorithme MiniMax.
     */
    public JoueurMinimax() {}

    @Override
    public String getNom() {
        return "Joueur Minimax";
    }

    @Override
    public void choisirPiece(Jeu jeu, Piece ignoree) {
        // Étape 1 : Construction de l'arbre des possibilités
        // L'IA Minimax agit ici en tant que "donneur de pièce" (Joueur.MIN),
        // elle cherche donc à donner la "pire" pièce possible à l'adversaire.
        Arborescence constructeur = new ArborescenceMinimax();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MIN);

        // Étape 2 : Analyse de l’arbre et choix du meilleur nœud selon heuristique
        // L’IA choisit le fils avec la plus faible heuristique (plus avantageux pour elle).
        NoeudArbre choix = trouverMeilleurFilsMin(racine);

        // Étape 3 : Récupération de la pièce à donner à l’adversaire
        // Si aucune pièce n’est spécifiée, on utilise une pièce par défaut (la première disponible).
        Piece pieceChoisie = (choix != null) ? recupererPieceOuDefaut(choix, jeu) : null;

        // Étape 4 : Mise à jour du jeu avec la pièce sélectionnée
        if (pieceChoisie != null) {
            jeu.setPieceCourante(pieceChoisie);
        }

        jeu.tourSuivant();
    }

    @Override
    public void placerPiece(Jeu jeu, Piece piece, Position ignoree) {
        List<Position> positions = jeu.getPlateau().getPositionsDisponibles();

        // Priorité 1 : gagner si possible
        Position positionGagnante = verifierVictoirePossible(jeu, piece, positions);
        if (positionGagnante != null) {
            appliquerPlacement(jeu, piece, positionGagnante);
            return;
        }

        // Priorité 2 : placer de manière stratégique via Minimax
        Position positionOptimale = rechercherMeilleurePositionViaMax(jeu, piece);
        if (positionOptimale != null) {
            appliquerPlacement(jeu, piece, positionOptimale);
            return;
        }

        // Fallback : n'importe quelle position libre
        if (!positions.isEmpty()) {
            appliquerPlacement(jeu, piece, positions.get(0));
        }
    }

    /**
     * Recherche la meilleure position où placer la pièce selon l'algorithme Minimax (côté MAX).
     * L’IA explore toutes les positions possibles et choisit celle qui maximise son heuristique.
     *
     * @param jeu   L’état actuel du jeu.
     * @param piece La pièce à placer.
     * @return La position jugée optimale, ou null si aucune n’est trouvée.
     */
    private Position rechercherMeilleurePositionViaMax(Jeu jeu, Piece piece) {
        // Étape 1 : Construire l’arbre des coups possibles pour le joueur MAX (l'IA joue)
        ArborescenceMinimax constructeur = new ArborescenceMinimax();
        NoeudArbre racine = constructeur.construireArbre(jeu, Joueur.MAX);

        // Étape 2 : Parcours des fils de la racine pour trouver le meilleur coup
        NoeudArbre meilleurChoix = null;
        double meilleureValeurHeuristique = Double.NEGATIVE_INFINITY;

        for (NoeudArbre noeudCandidat = racine.getFilsaine(); 
                        noeudCandidat != null; 
                        noeudCandidat = noeudCandidat.getFreredroit()) {

            double heuristiqueActuelle = noeudCandidat.getHeuristique();

            if (heuristiqueActuelle > meilleureValeurHeuristique) {
                meilleureValeurHeuristique = heuristiqueActuelle;
                meilleurChoix = noeudCandidat;
            }
        }

        // Étape 3 : Si un meilleur coup est trouvé, identifier la position modifiée
        if (meilleurChoix != null) {
            return detecterPositionChangee(jeu, meilleurChoix);
        }

        return null;
    }

    /**
     * Vérifie s’il existe une position menant à une victoire immédiate.
     *
     * @param jeu       Le jeu à simuler.
     * @param piece     La pièce à tester.
     * @param positions Liste des positions possibles.
     * @return La position gagnante si trouvée, sinon null.
     */
    private Position verifierVictoirePossible(Jeu jeu, Piece piece, List<Position> positions) {
        for (Position position : positions) {
            // On crée une copie du jeu pour faire une simulation de placement
            Jeu simulation = jeu.copier();

            // On tente de placer la pièce sur cette position dans la simulation
            simulation.getPlateau().placerPiece(piece, position);
            simulation.retirerPieceChoisit(piece);
            simulation.setPieceCourante(null); // Réinitialise la pièce courante pour éviter les conflits

            // Vérifie si cette simulation mène à une victoire pour l'IA
            if (simulation.aGagne() && !simulation.getJoueurGagnant().getNom().contains("Humain")) {
                return position; // Retourne immédiatement la position gagnante
            }
        }

        return null; // Aucune position ne mène à une victoire directe
    }

    /**
     * Recherche le meilleur nœud enfant de type MIN dans l’arbre de jeu.
     * L'objectif du joueur MIN (l'IA qui donne une pièce à l’adversaire) est de choisir la pièce
     * la moins avantageuse possible (i.e. celle avec la valeur d'heuristique la plus basse).
     *
     * @param racine Le nœud racine à partir duquel chercher.
     * @return Le meilleur nœud à suivre, ou une alternative si aucun nœud n’a une heuristique valide.
     */
    private NoeudArbre trouverMeilleurFilsMin(NoeudArbre racine) {
        NoeudArbre meilleurChoix = null;           // Meilleur nœud en termes d’heuristique
        NoeudArbre choixDeSecours = null;          // Alternative si aucune heuristique "meilleure" trouvée
        double valeurMinHeuristique = Double.POSITIVE_INFINITY;

        // Parcours de tous les fils du nœud racine (donc tous les coups possibles)
        for (NoeudArbre noeudCandidat = racine.getFilsaine();
                        noeudCandidat != null;
                        noeudCandidat = noeudCandidat.getFreredroit()) {

            double heuristique = noeudCandidat.getHeuristique();

            // Si le coup mène à une victoire directe (valeur infinie), on le garde comme secours
            if (heuristique == Double.POSITIVE_INFINITY && choixDeSecours == null) {
                choixDeSecours = noeudCandidat;
            // Sinon, on cherche la plus petite heuristique (c’est ce que veut le joueur MIN)
            } else if (heuristique < valeurMinHeuristique) {
                valeurMinHeuristique = heuristique;
                meilleurChoix = noeudCandidat;
            }
        }

        // On retourne le meilleur choix si trouvé, sinon l’alternative (victoire immédiate)
        return (meilleurChoix != null) ? meilleurChoix : choixDeSecours;
    }

    /**
     * Récupère la pièce associée au nœud de l’arbre ou une pièce par défaut si aucune n’est définie.
     * <p>
     * Cette méthode est utilisée lorsque le nœud de l’arbre sélectionné ne propose pas explicitement
     * une pièce à donner à l'adversaire. Dans ce cas, on prend arbitrairement la première pièce restante.
     * </p>
     *
     * @param noeudChoisi Le nœud contenant éventuellement la pièce à donner.
     * @param jeu         Le jeu en cours, permettant d'accéder aux pièces restantes.
     * @return La pièce à donner à l’adversaire, ou une pièce par défaut si nécessaire.
     */
    private Piece recupererPieceOuDefaut(NoeudArbre noeudChoisi, Jeu jeu) {
        // Essaye d’abord de récupérer la pièce proposée par le nœud de l’arbre
        Piece pieceProposee = noeudChoisi.getPieceDonnee();

        // Si aucune pièce n’est proposée, on prend la première pièce encore disponible
        if (pieceProposee == null && !jeu.getPiecesRestantes().isEmpty()) {
            pieceProposee = jeu.getPiecesRestantes().get(0);
        }

        return pieceProposee;
    }

    /**
     * Détecte la position sur le plateau qui a été modifiée entre deux états du jeu.
     * <p>
     * Cette méthode compare la grille actuelle avec celle issue d’un nœud de l’arbre
     * pour identifier l’endroit où une pièce a été placée. Elle est utilisée pour
     * retrouver la position d’un coup simulé dans l’arbre de recherche.
     * </p>
     *
     * @param jeu          Le jeu actuel (avant l’opération).
     * @param noeud        Le nœud de l’arbre contenant l’état du jeu modifié (après le coup).
     * @return La position (ligne, colonne) où une pièce a été placée, ou {@code null} si aucune différence n'est trouvée.
     */
    private Position detecterPositionChangee(Jeu jeu, NoeudArbre noeud) {
        // Grille avant le coup simulé
        Piece[][] grilleAvant = jeu.getPlateau().getGrille();
        // Grille après le coup simulé (provenant du nœud de l’arbre)
        Piece[][] grilleApres = noeud.getEtatJeu().getPlateau().getGrille();

        // Parcours des cellules pour trouver celle qui a changé (vide → occupée)
        for (int ligne = 0; ligne < Plateau.TAILLE; ligne++) {
            for (int colonne = 0; colonne < Plateau.TAILLE; colonne++) {
                if (grilleAvant[ligne][colonne] == null && grilleApres[ligne][colonne] != null) {
                    return new Position(ligne, colonne); // Changement détecté
                 }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "C'est le tour du joueur Minimax";
    }
}