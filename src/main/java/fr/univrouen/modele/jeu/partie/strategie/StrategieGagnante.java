package fr.univrouen.modele.jeu.partie.strategie;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Interface représentant une stratégie de vérification de condition gagnante
 * dans le jeu Quarto.
 * 
 * Cette interface définit une méthode permettant de vérifier si un joueur a gagné
 * selon une condition spécifique de stratégie, basée sur l'alignement ou la disposition
 * des pièces sur le plateau de jeu.
 * 
 * Les classes qui implémentent cette interface définissent des stratégies concrètes,
 * comme la vérification d'alignements horizontaux, verticaux, diagonaux, ou de carrés
 * de différentes tailles (petits, grands, tournants, etc.), en fonction des règles du jeu.
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public interface StrategieGagnante {

    /**
     * Vérifie si un joueur a gagné selon la stratégie définie.
     * 
     * Cette méthode évalue le plateau de jeu pour détecter une configuration gagnante,
     * selon la stratégie implémentée (par exemple,
     * alignement de pièces sur un carré, sur une ligne, etc.).
     * 
     * @param plateau Le plateau de jeu (une matrice 4x4 de pièces).
     * @return true si un alignement gagnant a été trouvé, sinon false.
     * @see Piece
     */
    boolean verifierGagnant(Piece[][] plateau);
}