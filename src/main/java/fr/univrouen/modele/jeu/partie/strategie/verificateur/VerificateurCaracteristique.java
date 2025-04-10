package fr.univrouen.modele.jeu.partie.strategie.verificateur;

import java.util.List;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Interface représentant un vérificateur de caractéristiques des pièces dans le jeu Quarto.
 * Cette interface fait partie du patron Chain of Responsibility. Chaque vérificateur est
 * responsable de vérifier une caractéristique spécifique d'une pièce (par exemple, la forme,
 * la couleur, la hauteur ou le remplissage) par rapport à une autre pièce.
 * <p>
 * Elle permet également de chaîner plusieurs vérificateurs ensemble, chaque vérificateur
 * ayant la possibilité de déléguer la vérification au vérificateur suivant dans la chaîne.
 * </p>
 * 
 * <p>Exemple : Si un vérificateur de forme échoue, il peut transmettre la vérification à un 
 * vérificateur de couleur, puis à un vérificateur de hauteur, etc.</p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public interface VerificateurCaracteristique {

    /**
     * Vérifie si les quatre pièces données respectent une certaine règle pour la caractéristique
     * gérée par ce vérificateur.
     * 
     * @param pieces La liste des pièces à vérifier.
     * @return true si les quatre pièces respectent la règle, sinon false.
     * @see Piece
     */
    boolean verifier(List<Piece> pieces);

    /**
     * Définit le vérificateur suivant dans la chaîne de responsabilité.
     * Cela permet de chaîner plusieurs vérificateurs pour qu'ils vérifient des caractéristiques
     * successivement. Si une vérification échoue, elle peut être passée au vérificateur suivant.
     * 
     * @param suivant Le vérificateur suivant à chaîner.
     * @return Le vérificateur suivant après la configuration.
     */
    VerificateurCaracteristique setSuivant(VerificateurCaracteristique suivant);
}