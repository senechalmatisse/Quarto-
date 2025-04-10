package fr.univrouen.controleur.jeu.interaction;

import javax.swing.*;
import java.awt.*;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.joueur.Joueur;

/**
 * Contrôleur responsable de la gestion de la fin de partie dans le jeu Quarto.
 * <p>
 * Il permet d'afficher une boîte de dialogue indiquant le résultat (victoire ou match nul),
 * et ferme automatiquement la fenêtre principale après confirmation.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class FinPartieControleur {
    /** Titre affiché dans la popup de fin de partie. */
    private static final String TITRE_POPUP = "Fin de la Partie";

    /**
     * Traite la fin de la partie en affichant une popup de résultat,
     * puis ferme la fenêtre parente si l'utilisateur clique sur OK.
     *
     * @param parent Composant Swing de référence (ex. : PlateauPanel).
     * @param jeu    L'état actuel du jeu.
     */
    public static void traiterFinPartie(Component parent, Jeu jeu) {
        if (jeu == null || parent == null) return;

        String message = genererMessageFinPartie(jeu);
        Window window = SwingUtilities.getWindowAncestor(parent);

        int choix = JOptionPane.showConfirmDialog(
            window,
            message,
            TITRE_POPUP,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (choix == JOptionPane.OK_OPTION || choix == JOptionPane.CLOSED_OPTION) {
            if (window != null) window.dispose();
        }
    }

    /**
     * Génère le message à afficher dans la popup de fin de partie.
     *
     * @param jeu Le jeu dont on veut extraire le résultat.
     * @return Un message de victoire ou de match nul.
     */
    private static String genererMessageFinPartie(Jeu jeu) {
        Joueur gagnant = jeu.estGagnant();
        return (gagnant != null)
                ? "La partie est terminée !\n" + gagnant.getNom() + " a gagné !"
                : "La partie est terminée ! Match nul.";
    }
}