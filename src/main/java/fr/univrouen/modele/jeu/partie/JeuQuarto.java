package fr.univrouen.modele.jeu.partie;

import java.util.*;

import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.plateau.*;
import fr.univrouen.modele.jeu.piece.caracteristique.*;
import fr.univrouen.modele.jeu.partie.observer.Observer;
import fr.univrouen.modele.jeu.partie.strategie.StrategieGagnante;

/**
 * Classe représentant le jeu Quarto.
 * Elle gère la logique du jeu, la gestion des joueurs, des pièces, ainsi que la vérification des conditions de victoire.
 * Elle implémente l'interface {@link Jeu} pour définir les actions possibles dans le jeu.
 * 
 * <p>Le jeu se joue avec deux joueurs qui s'affrontent en plaçant des pièces sur un plateau de 4x4. Chaque pièce a
 * des caractéristiques uniques et l'objectif est de réaliser une combinaison gagnante de 4 pièces alignées selon
 * certaines caractéristiques communes.</p>
 * 
 * @see Jeu
 * @author Matisse SENECHAL
 * @version 4.1
 * @since JDK 17
 */
public class JeuQuarto implements Jeu {
    /** Observateurs enregistrés pour mise à jour de l'interface. */
    private List<Observer> observers = new ArrayList<>();

    /** Stratégie gagnante utilisée pour déterminer la victoire. */
    private StrategieGagnante strategie;

    /** Plateau de jeu 4x4. */
    private Plateau plateau;

    /** Pièce actuellement sélectionnée pour être placée. */
    private Piece pieceCourante;

    /**
     * Liste complète des pièces du jeu.
     * Liste des pièces restantes à jouer.
     */
    private List<Piece> pieces, piecesRestantes;

    /** Joueurs de la partie. */
    private Joueur joueur1, joueur2, joueurActuel;

    /**
     * Construit une partie de Quarto avec deux joueurs et une stratégie gagnante.
     *
     * @param joueur1   Premier joueur.
     * @param joueur2   Deuxième joueur.
     * @param strategie Stratégie de victoire à utiliser.
     */
    public JeuQuarto(Joueur joueur1, Joueur joueur2, StrategieGagnante strategie) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.strategie = strategie;
        this.commencerJeu();
    }

    @Override
    public Joueur getJoueurActuel() {
        return this.joueurActuel;
    }

    @Override
    public Plateau getPlateau() {
        return this.plateau;
    }

    @Override
    public Piece getPieceCourante() {
        return pieceCourante;
    }

    @Override
    public void setPieceCourante(Piece nouvellePieceCourante) {
        this.pieceCourante = nouvellePieceCourante;
        notifyObservers();
    }

    @Override
    public List<Piece> getPiecesRestantes() {
        return piecesRestantes;
    }

    /**
     * Initialise le plateau, les pièces et les états de jeu.
     */
    private void commencerJeu() {
        this.plateau = new Plateau();
        this.pieces = genererPieces();
        this.piecesRestantes = new ArrayList<>(pieces);
        this.joueurActuel = this.joueur1;
        this.pieceCourante = null;
    }

    @Override
    public void tourSuivant() {
        joueurActuel = (joueurActuel.equals(joueur1)) ? joueur2 : joueur1;
        notifyObservers();
    }

    /**
     * Génère les 16 pièces uniques du jeu (chaque combinaison de caractéristiques).
     *
     * @return Liste de toutes les pièces possibles.
     * @see Piece
     */
    private List<Piece> genererPieces() {
        List<Piece> pieces = new ArrayList<>(16);

        for (Hauteur h : Hauteur.values()) {
            for (Forme f : Forme.values()) {
                for (Remplissage r : Remplissage.values()) {
                    for (Couleur c : Couleur.values()) {
                        pieces.add(new Piece(h, f, r, c));
                    }
                }
            }
        }

        return pieces;
    }

    @Override
    public boolean estTerminee() {
        return aGagne() || piecesRestantes.isEmpty();
    }

    @Override
    public Joueur estGagnant() {
        return piecesRestantes.isEmpty() ? null : joueurActuel;
    }

    @Override
    public void retirerPieceChoisit(Piece pieceChoisit) {
        piecesRestantes.remove(pieceChoisit);
    }

    @Override
    public boolean debutPartie() {
        return piecesRestantes.size() == 16 && pieceCourante == null && plateau.estVide();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Vérifie si une condition de victoire est remplie.
     *
     * @return {@code true} si un joueur a gagné, {@code false} sinon.
     */
    public boolean aGagne() {
        return strategie.verifierGagnant(plateau.getGrille());
    }

    /**
     * Détermine le joueur ayant gagné la partie.
     *
     * @return Le joueur gagnant ou {@code null} si la partie continue.
     */
    public Joueur getJoueurGagnant() {
        return aGagne() ? joueurActuel : null;
    }

    @Override
    public Jeu copier() {
        JeuQuarto copie = new JeuQuarto(this.joueur1, this.joueur2, this.strategie);
        copie.plateau = this.plateau.copier();
        copie.pieces = new ArrayList<>(this.pieces);
        copie.piecesRestantes = new ArrayList<>(this.piecesRestantes);
        copie.pieceCourante = this.pieceCourante;
        copie.joueurActuel = this.joueurActuel;
        return copie;
    }
}