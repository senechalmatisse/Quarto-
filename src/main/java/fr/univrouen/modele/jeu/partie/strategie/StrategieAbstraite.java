package fr.univrouen.modele.jeu.partie.strategie;

import java.util.List;

import fr.univrouen.modele.jeu.partie.strategie.verificateur.*;
import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe abstraite représentant une stratégie de vérification de condition gagnante
 * dans le jeu Quarto.
 * 
 * Cette classe sert de base pour toutes les stratégies de vérification de victoire,
 * qu'elles soient basées sur des alignements de pièces dans différentes configurations
 * (par exemple, les petits carrés, grands carrés, etc.).
 * 
 * Elle utilise un mécanisme de vérification de caractéristiques des pièces pour 
 * déterminer si deux pièces partagent une caractéristique en commun, ce qui est nécessaire
 * pour qu'un alignement soit considéré comme gagnant. La chaîne de responsabilité
 * dans le vérificateur permet de vérifier plusieurs caractéristiques (hauteur, couleur, remplissage, forme) de manière séquentielle.
 * 
 * @author Matisse SENECHAL
 * @see StrategieGagnante
 * @see VerificateurCaracteristique
 * @version 1.0
 * @since JDK 17
 */
public abstract class StrategieAbstraite implements StrategieGagnante {
    /** Le vérificateur de caractéristiques qui permet de comparer deux pièces. */
    private VerificateurCaracteristique verificateur;

    /**
     * Constructeur par défaut qui initialise le vérificateur de caractéristiques.
     * Le vérificateur est configuré comme une chaîne de responsabilité qui vérifie
     * les caractéristiques dans l'ordre suivant : hauteur, couleur, remplissage, forme.
     * 
     * @see VerificateurHauteur
     * @see VerificateurCouleur
     * @see VerificateurRemplissage
     * @see VerificateurForme
     * @see VerificateurCaracteristique#setSuivant(VerificateurCaracteristique) 
     */
    public StrategieAbstraite() {
        verificateur = new VerificateurHauteur();
        verificateur.setSuivant(new VerificateurCouleur())
                    .setSuivant(new VerificateurRemplissage())
                    .setSuivant(new VerificateurForme());
    }

    /**
     * Vérifie si deux pièces ont la même caractéristique en utilisant la chaîne de responsabilité.
     * Le vérificateur vérifie successivement si les pièces partagent une caractéristique commune,
     * selon l'ordre défini dans la chaîne de responsabilité (hauteur, couleur, remplissage, forme).
     * 
     * @param pieces La liste des pièces à comparer.
     * @return true si les deux pièces partagent une caractéristique en commun, sinon false.
     * @throws IllegalArgumentException si l'une des pièces est null.
     * @see Piece
     * @see VerificateurCaracteristique#verifier(java.util.List)
     */
    protected boolean verifierCaracteristiques(List<Piece> pieces) {
        return verificateur.verifier(pieces);
    }
}