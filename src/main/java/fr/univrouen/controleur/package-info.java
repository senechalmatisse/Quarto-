/**
 * Contient les contrôleurs de l'application Quarto.
 * <p>
 * Le package {@code controleur} regroupe les classes chargées de la gestion des interactions entre la vue
 * (interface graphique) et le modèle (logique métier du jeu). Il suit le patron MVC (Modèle-Vue-Contrôleur).
 * </p>
 *
 * <p>
 * Il est structuré en plusieurs sous-packages :
 * </p>
 * <ul>
 *   <li>{@code controleur.jeu.interaction} : Gère les événements de jeu en cours (placement, clics, fin de partie...)</li>
 *   <li>{@code controleur.jeu.selection} : Gère la phase de sélection des pièces à donner à l’adversaire.</li>
 *   <li>{@code controleur.util} : Classes utilitaires pour la configuration des joueurs.</li>
 * </ul>
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
package fr.univrouen.controleur;