package fr.univrouen.modele.arbre;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.Position;

/**
 * Représente un nœud de l'arbre de recherche utilisé dans l'algorithme Minimax.
 * Chaque nœud contient un état de jeu, un joueur (MIN ou MAX),
 * une heuristique, des références vers ses fils et son parent,
 * ainsi que des informations sur la pièce donnée ou la position jouée.
 * 
 * <p>
 * L’arbre ainsi construit permet d’explorer les différentes configurations du jeu
 * pour évaluer les meilleurs coups possibles.
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 1.3
 * @since JDK 17
 */
public class NoeudArbre {
    /** Le joueur (MIN ou MAX) auquel le nœud appartient. */
    private Joueur etiquette;

    /** Référence vers le fils aîné (premier enfant du nœud). */
    private NoeudArbre filsaine;

    /** Référence vers le frère droit (suivant au même niveau dans l'arbre). */
    private NoeudArbre freredroit;

    /** Référence vers le nœud parent. */
    private NoeudArbre pere;

    /** Valeur heuristique associée à ce nœud. */
    private double heuristique;

    /** Valeur heuristique associée à ce nœud. */
    private Jeu etatJeu;

    /** Pièce donnée à l’adversaire (si applicable à ce niveau). */
    private Piece pieceDonnee;

    /** Position sur laquelle une pièce a été placée (si applicable à ce niveau). */
    private Position positionJouee;

    /**
     * Construit un nouveau nœud avec un joueur et un état de jeu donné.
     *
     * @param etiquette Le joueur (MIN ou MAX) associé à ce nœud.
     * @param etatJeu   L'état du jeu correspondant à ce nœud.
     */
    public NoeudArbre(final Joueur etiquette, final Jeu etatJeu) {
        this.etiquette = etiquette;
        this.etatJeu = etatJeu;
    }

    /**
     * Retourne le joueur associé à ce nœud.
     *
     * @return Le joueur (MIN ou MAX).
     */
    public Joueur getEtiquette() {
        return etiquette;
    }

    /**
     * Définit le joueur associé à ce nœud.
     *
     * @param etiquette Le joueur (MIN ou MAX).
     */
    public void setEtiquette(final Joueur etiquette) {
        this.etiquette = etiquette;
    }

    /**
     * Retourne le fils aîné de ce nœud.
     *
     * @return Le premier enfant du nœud.
     */
    public NoeudArbre getFilsaine() {
        return filsaine;
    }

    /**
     * Définit le fils aîné du nœud et met à jour son parent.
     *
     * @param filsaine Le nœud enfant.
     */
    public void setFilsaine(final NoeudArbre filsaine) {
        this.filsaine = filsaine;
        updateLienParent(filsaine);
    }

    /**
     * Retourne le frère droit de ce nœud.
     *
     * @return Le nœud frère suivant.
     */
    public NoeudArbre getFreredroit() {
        return freredroit;
    }

    /**
     * Définit le frère droit du nœud et met à jour son parent.
     *
     * @param freredroit Le nœud frère droit.
     */
    public void setFreredroit(final NoeudArbre freredroit) {
        this.freredroit = freredroit;
        updateLienParent(freredroit);
    }

    /**
     * Retourne le parent du nœud.
     *
     * @return Le nœud parent.
     */
    public NoeudArbre getPere() {
        return pere;
    }

    /**
     * Définit le parent du nœud.
     *
     * @param pere Le nœud parent.
     */
    public void setPere(final NoeudArbre pere) {
        this.pere = pere;
    }

    /**
     * Retourne la valeur heuristique associée à ce nœud.
     *
     * @return La valeur heuristique.
     */
    public double getHeuristique() {
        return heuristique;
    }

    /**
     * Définit la valeur heuristique du nœud.
     *
     * @param heuristique La valeur heuristique.
     */
    public void setHeuristique(final double heuristique) {
        this.heuristique = heuristique;
    }

    /**
     * Retourne l'état du jeu représenté par ce nœud.
     *
     * @return L'état du jeu.
     */
    public Jeu getEtatJeu() {
        return etatJeu;
    }

    /**
     * Définit l'état du jeu associé à ce nœud.
     *
     * @param etatJeu L'état du jeu.
     */
    public void setEtatJeu(final Jeu etatJeu) {
        this.etatJeu = etatJeu;
    }

    /**
     * Retourne la pièce donnée à l’adversaire dans ce nœud.
     *
     * @return La pièce donnée.
     */
    public Piece getPieceDonnee() {
        return pieceDonnee;
    }

    /**
     * Définit la pièce donnée à l’adversaire.
     *
     * @param pieceDonnee La pièce choisie.
     */
    protected void setPieceDonnee(final Piece pieceDonnee) {
        this.pieceDonnee = pieceDonnee;
    }

    /**
     * Retourne la position sur laquelle une pièce a été jouée dans cet état.
     *
     * @return La position jouée.
     */
    public Position getPositionJouee() {
        return positionJouee;
    }

    /**
     * Définit la position de placement de la pièce dans ce nœud.
     *
     * @param position La position sur le plateau.
     */
    public void setPositionJouee(final Position position) {
        this.positionJouee = position;
    }

    /**
     * Met à jour la référence du parent pour un nœud enfant.
     * 
     * @param enfant Le nœud enfant dont le parent doit être mis à jour.
     */
    private void updateLienParent(final NoeudArbre enfant) {
        if (enfant != null) {
            enfant.setPere(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Noeud{joueur=%s, h=%.2f, pièce=%s, pos=%s}", 
            etiquette, heuristique, pieceDonnee, positionJouee);
    }
}