package fr.univrouen.modele.jeu.piece;

import fr.univrouen.modele.jeu.piece.caracteristique.*;

/**
 * Représente une pièce du jeu Quarto, caractérisée par quatre attributs : hauteur, forme, remplissage et couleur.
 * Cette classe encapsule les caractéristiques d'une pièce de jeu
 * et fournit des méthodes pour les récupérer et comparer des pièces.
 * 
 * <p>Une pièce est définie par les attributs suivants :</p>
 * <ul>
 *     <li>Hauteur : soit haute, soit basse.</li>
 *     <li>Forme : soit ronde, soit carrée.</li>
 *     <li>Remplissage : soit plein, soit creux.</li>
 *     <li>Couleur : soit claire, soit foncée.</li>
 * </ul>
 * 
 * @see Hauteur
 * @see Forme
 * @see Remplissage
 * @see Couleur
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class Piece {
    /** La hauteur de la pièce : {@code HAUTE} ou {@code BASSE}. */
    private final Hauteur HAUTEUR;

    /** La forme de la pièce : {@code RONDE} ou {@code CARREE}. */
    private final Forme FORME;

    /** La forme de la pièce : {@code RONDE} ou {@code CARREE}. */
    private final Remplissage REMPLISSAGE;

    /** La couleur de la pièce : {@code CLAIRE} ou {@code FONCEE}. */
    private final Couleur COULEUR;

    /**
     * Constructeur de la classe Piece avec les caractéristiques spécifiées.
     * 
     * @param hauteur La hauteur de la pièce (Hauteur.HAUTE ou Hauteur.BASSE).
     * @param forme La forme de la pièce (Forme.RONDE ou Forme.CARREE).
     * @param remplissage Le remplissage de la pièce (Remplissage.PLEIN ou Remplissage.CREUX).
     * @param couleur La couleur de la pièce (Couleur.CLAIRE ou Couleur.FONCEE).
     * @throws IllegalArgumentException Si l'un des paramètres est nul.
     */
    public Piece(Hauteur hauteur, Forme forme, Remplissage remplissage, Couleur couleur) {
        if (hauteur == null || forme == null || remplissage == null || couleur == null) {
            throw new IllegalArgumentException("Tous les attributs doivent être non nuls.");
        }

        this.HAUTEUR = hauteur;
        this.FORME = forme;
        this.REMPLISSAGE = remplissage;
        this.COULEUR = couleur;
    }

    /**
     * Retourne la hauteur de la pièce.
     * 
     * @return La hauteur de la pièce.
     * @see Hauteur
     */
    public Hauteur getHauteur() {
        return HAUTEUR;
    }

    /**
     * Retourne la forme de la pièce.
     * 
     * @return La forme de la pièce.
     * @see Forme
     */
    public Forme getForme() {
        return FORME;
    }

    /**
     * Retourne le remplissage de la pièce.
     * 
     * @return Le remplissage de la pièce.
     * @see Remplissage
     */
    public Remplissage getRemplissage() {
        return REMPLISSAGE;
    }

    /**
     * Retourne la couleur de la pièce.
     * 
     * @return La couleur de la pièce.
     * @see Couleur
     */
    public Couleur getCouleur() {
        return COULEUR;
    }

    /**
     * Convertit les caractéristiques d'une pièce en tableau de booléens.
     * <p>Utilisé pour des comparaisons simples dans les algorithmes comme Minimax.</p>
     *
     * @param p La pièce à convertir.
     * @return Un tableau de 4 booléens représentant les attributs (ordre : hauteur, forme, remplissage, couleur).
     */
    public static boolean[] getAttributs(Piece p) {
        return new boolean[] {
            p.getHauteur() == Hauteur.HAUTE,
            p.getForme() == Forme.CARREE,
            p.getRemplissage() == Remplissage.PLEIN,
            p.getCouleur() == Couleur.FONCEE
        };
    }

    @Override
    public String toString() {
        return String.format("Piece(%s, %s, %s, %s)", HAUTEUR, FORME, REMPLISSAGE, COULEUR);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Piece piece = (Piece) obj;
        return HAUTEUR == piece.HAUTEUR &&
               FORME == piece.FORME &&
               REMPLISSAGE == piece.REMPLISSAGE &&
               COULEUR == piece.COULEUR;
    }
}