package fr.univrouen.controleur.util;

import fr.univrouen.modele.joueur.Joueur;

/**
 * Contrôleur utilitaire chargé d’analyser le type d’un joueur (humain ou IA).
 * <p>
 * Cette classe permet de centraliser la logique permettant de déterminer
 * si un joueur est humain ou contrôlé par une intelligence artificielle.
 * Elle est notamment utilisée pour adapter les comportements du jeu et l’affichage.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class JoueurControleur {

    /**
     * Détermine si le joueur donné est un joueur humain.
     * <p>
     * Cette vérification est actuellement basée sur le nom du joueur
     * contenant le mot-clé « humain » (insensible à la casse).
     * </p>
     *
     * @param joueur Le joueur à analyser.
     * @return {@code true} s'il s'agit d'un humain, {@code false} s'il s'agit d'une IA.
     */
    public static boolean estHumain(Joueur joueur) {
        return joueur != null && joueur.getNom().toLowerCase().contains("humain");
    }
}