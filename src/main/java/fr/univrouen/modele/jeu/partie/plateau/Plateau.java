package fr.univrouen.modele.jeu.partie.plateau;

import java.util.*;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Représente un plateau de jeu Quarto de taille 4x4.
 * Chaque case du plateau peut contenir une pièce ou être vide.
 * Fournit des méthodes pour placer des pièces, vérifier les cases disponibles,
 * copier l’état du plateau ou extraire les alignements utiles à l’évaluation.
 * 
 * @author Matisse SENECHAL
 * @version 2.0
 * @since JDK 17
 */
public class Plateau {
    /** Taille standard du plateau (4x4). */
    public static final int TAILLE = 4;

    /** Grille interne représentant les cases du plateau. */
    private Piece[][] grille;

    /**
     * Initialise un nouveau plateau vide (4x4), sans pièces placées.
     */
    public Plateau() {
        this.grille = new Piece[TAILLE][TAILLE];
    }

    /**
     * Récupère la grille actuelle du plateau.
     *
     * @return un tableau 2D de pièces représentant l’état du plateau.
     */
    public Piece[][] getGrille() {
        return this.grille;
    }

    /**
     * Place une pièce à une position donnée si la case est libre.
     *
     * @param piece    La pièce à placer.
     * @param position La position où placer la pièce.
     * @throws IllegalArgumentException si la case est déjà occupée.
     */
    public void placerPiece(Piece piece, Position position) {
        int ligne = position.getX();
        int colonne = position.getY();

        if (grille[ligne][colonne] != null) {
            throw new IllegalArgumentException("La case (" + ligne + "," + colonne + ") est déjà occupée.");
        }

        grille[ligne][colonne] = piece;
    }

    /**
     * Vérifie si une case spécifique du plateau est libre.
     *
     * @param position La position à vérifier.
     * @return {@code true} si la case est vide, {@code false} sinon.
     */
    public boolean estLibre(Position position) {
        return grille[position.getX()][position.getY()] == null;
    }

    /**
     * Récupère la pièce présente à une position donnée.
     *
     * @param position La position à consulter.
     * @return La pièce à cette position, ou {@code null} si la case est vide.
     */
    public Piece getPiece(Position position) {
        return grille[position.getX()][position.getY()];
    }

    /**
     * Vérifie si le plateau est entièrement vide (aucune pièce placée).
     *
     * @return {@code true} si aucune case n’est occupée, sinon {@code false}.
     */
    public boolean estVide() {
        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE; colonne++) {
                if (grille[ligne][colonne] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Crée une copie profonde du plateau.
     * Utile pour les simulations (notamment IA).
     *
     * @return Un nouveau plateau avec les mêmes pièces aux mêmes positions.
     */
    public Plateau copier() {
        Plateau clone = new Plateau();

        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE; colonne++) {
                Piece piece = this.grille[ligne][colonne];
                if (piece != null) {
                    clone.placerPiece(piece, new Position(ligne, colonne));
                }
            }
        }

        return clone;
    }

    /**
     * Récupère toutes les positions disponibles (cases vides) sur le plateau.
     *
     * @return Une liste des positions libres.
     */
    public List<Position> getPositionsDisponibles() {
        List<Position> casesLibres = new ArrayList<>();

        for (int ligne = 0; ligne < TAILLE; ligne++) {
            for (int colonne = 0; colonne < TAILLE; colonne++) {
                if (grille[ligne][colonne] == null) {
                    casesLibres.add(new Position(ligne, colonne));
                }
            }
        }

        return casesLibres;
    }

    /**
     * Récupère tous les alignements potentiels sur le plateau (lignes, colonnes, diagonales).
     * <p>
     * Ces alignements sont utilisés pour évaluer s'il existe une combinaison gagnante.
     * Chaque alignement est représenté par une liste de 4 pièces (éventuellement avec des cases vides).
     * </p>
     *
     * @return Une liste de toutes les lignes, colonnes et diagonales du plateau.
     */
    public List<List<Piece>> getAlignementsPotentiels() {
        List<List<Piece>> alignements = new ArrayList<>(10); // 4 lignes + 4 colonnes + 2 diagonales

        // Lignes et colonnes
        for (int i = 0; i < TAILLE; i++) {
            List<Piece> ligne = new ArrayList<>(TAILLE);
            List<Piece> colonne = new ArrayList<>(TAILLE);

            for (int j = 0; j < TAILLE; j++) {
                ligne.add(grille[i][j]); // Ligne i
                colonne.add(grille[j][i]); // Colonne i
            }

            alignements.add(ligne);
            alignements.add(colonne);
        }

        // Diagonales
        List<Piece> diagonalePrincipale = new ArrayList<>(TAILLE);
        List<Piece> diagonaleSecondaire = new ArrayList<>(TAILLE);

        for (int i = 0; i < TAILLE; i++) {
            diagonalePrincipale.add(grille[i][i]);
            diagonaleSecondaire.add(grille[i][TAILLE - 1 - i]);
        }

        alignements.add(diagonalePrincipale);
        alignements.add(diagonaleSecondaire);

        return alignements;
    }
}