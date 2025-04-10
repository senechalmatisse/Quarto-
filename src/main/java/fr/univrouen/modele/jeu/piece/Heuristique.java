package fr.univrouen.modele.jeu.piece;

import java.util.List;

/**
 * Classe utilitaire permettant de calculer une heuristique sur l'état du plateau
 * dans le jeu Quarto. Cette heuristique est utilisée notamment dans les stratégies
 * d'IA (comme Minimax) pour évaluer les positions favorables.
 * 
 * <p>
 * Elle attribue des scores aux alignements de pièces en fonction du nombre
 * de caractéristiques communes entre les pièces alignées (forme, hauteur,
 * couleur, remplissage).
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class Heuristique {

    /**
     * Calcule un score heuristique global pour une liste d’alignements.
     * <p>
     * Chaque alignement est analysé, et un score est attribué en fonction
     * du nombre de caractéristiques communes entre les pièces :
     * <ul>
     *     <li>4 caractéristiques communes : +1000 points</li>
     *     <li>3 caractéristiques communes : +100 points</li>
     *     <li>2 caractéristiques communes : +10 points</li>
     *     <li>1 caractéristique commune : +1 point</li>
     *     <li>0 ou moins : 0 point</li>
     * </ul>
     *
     * @param alignements Liste des alignements (lignes, colonnes, diagonales).
     * @return Un score heuristique global pour le plateau.
     */
    public static int evaluerAlignements(List<List<Piece>> alignements) {
        int total = 0;
    
        for (List<Piece> ligne : alignements) {
            int communs = compterAttributsCommuns(ligne);
    
            switch (communs) {
                case 4:
                    total += 1000;
                    break;
                case 3:
                    total += 100;
                    break;
                case 2:
                    total += 10;
                    break;
                case 1:
                    total += 1;
                    break;
                default:
                    break; // Aucun point si 0 ou moins
            }
        }
    
        return total;
    }   

    /**
     * Compte combien d’attributs (forme, hauteur, couleur, remplissage)
     * sont communs entre les pièces d’un alignement.
     * 
     * <p>Les pièces nulles sont ignorées. Si moins de deux pièces
     * sont présentes, aucun attribut ne peut être comparé.</p>
     *
     * @param ligne Un alignement (généralement de 4 cases) contenant des pièces ou des cases vides.
     * @return Le nombre d’attributs identiques parmi les pièces de l’alignement (entre 0 et 4).
     */
    public static int compterAttributsCommuns(List<Piece> ligne) {
        int communs = 0;

        List<Piece> pieces = ligne.stream().filter(p -> p != null).toList();
        if (pieces.size() < 2) return 0;

        if (tousEgaux(pieces.stream().map(Piece::getHauteur).toList())) communs++;
        if (tousEgaux(pieces.stream().map(Piece::getForme).toList())) communs++;
        if (tousEgaux(pieces.stream().map(Piece::getRemplissage).toList())) communs++;
        if (tousEgaux(pieces.stream().map(Piece::getCouleur).toList())) communs++;

        return communs;
    }

    /**
     * Vérifie si tous les éléments d'une liste sont identiques.
     *
     * @param valeurs Liste d'éléments de même type.
     * @param <T> Type des éléments (ex. : Hauteur, Couleur, etc.).
     * @return {@code true} si tous les éléments sont égaux entre eux, sinon {@code false}.
     */
    private static <T> boolean tousEgaux(List<T> valeurs) {
        if (valeurs.isEmpty()) return false;
        T premier = valeurs.get(0);
        for (T val : valeurs) {
            if (!val.equals(premier)) return false;
        }
        return true;
    }
}