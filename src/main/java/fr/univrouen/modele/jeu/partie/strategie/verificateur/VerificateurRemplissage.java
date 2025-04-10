package fr.univrouen.modele.jeu.partie.strategie.verificateur;

import java.util.List;

import fr.univrouen.modele.jeu.piece.Piece;

/**
 * Vérificateur de la caractéristique "remplissage" des pièces dans le jeu Quarto.
 * Cette classe fait partie de la chaîne de responsabilité et permet de vérifier si
 * deux pièces ont le même remplissage. Si ce n'est pas le cas, elle passe la vérification
 * au vérificateur suivant dans la chaîne.
 * 
 * <p>Elle implémente la méthode {@link VerificateurCaracteristique#verifier(java.util.List)}
 * pour comparer les remplissages des deux pièces et, si elles ne correspondent pas, déléguer la vérification
 * au vérificateur suivant dans la chaîne.</p>
 * 
 * @see VerificateurAbstrait
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class VerificateurRemplissage extends VerificateurAbstrait {

    @Override
    public boolean verifier(List<Piece> pieces) {
        // Vérifier que la liste contient bien 4 pièces
        if (pieces.size() != 4) {
            return false;
        }

        // Comparaison du remplissage des pièces
        Piece piece1 = pieces.get(0);
        Piece piece2 = pieces.get(1);
        Piece piece3 = pieces.get(2);
        Piece piece4 = pieces.get(3);

        // Comparaison du remplissage des 4 pièces
        if (piece1.getRemplissage() == piece2.getRemplissage() &&
            piece1.getRemplissage() == piece3.getRemplissage() &&
            piece1.getRemplissage() == piece4.getRemplissage()) {
            return true;  // Toutes les pièces ont la même remplissage
        }

        // Si les remplissages ne correspondent pas, on passe la vérification au suivant
        return verifierSuivant(pieces);
    }
}