package fr.univrouen.modele.jeu.partie.strategie.verificateur;

import java.util.List;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Classe abstraite représentant un vérificateur de caractéristiques des pièces.
 * Elle implémente le patron Chain of Responsibility en permettant de chaîner les vérificateurs.
 * Chaque sous-classe doit implémenter la méthode {@link VerificateurCaracteristique#verifier(java.util.List)} pour définir
 * la logique spécifique de vérification d'une caractéristique (hauteur, couleur, forme, remplissage).
 * 
 * <p>Cette classe permet de chaîner plusieurs vérificateurs, chacun responsable de vérifier
 * une caractéristique spécifique (par exemple, la couleur, la forme, etc.). Si un vérificateur échoue
 * à vérifier les pièces, la vérification est transmise au vérificateur suivant dans la chaîne.</p>
 * 
 * @see VerificateurCaracteristique
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public abstract class VerificateurAbstrait implements VerificateurCaracteristique {
    /** Le vérificateur suivant dans la chaîne de responsabilité. */
    protected VerificateurCaracteristique suivant;

    @Override
    public VerificateurCaracteristique setSuivant(VerificateurCaracteristique suivant) {
        this.suivant = suivant;
        return suivant;
    }

    /**
     * Méthode utilitaire pour vérifier la responsabilité suivante dans la chaîne.
     * 
     * @param pieces Liste des pièces à vérifier.
     * @return true si le vérificateur suivant peut confirmer la condition, sinon false.
     */
    protected boolean verifierSuivant(List<Piece> pieces) {
        if (suivant != null) {
            return suivant.verifier(pieces);
        }

        return false;
    }
}