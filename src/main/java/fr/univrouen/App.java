package fr.univrouen;

import javax.swing.*;

import fr.univrouen.vue.AccueilFrame;
import fr.univrouen.controleur.AccueilFrameControleur;

/**
 * Classe principale de l'application qui lance l'interface graphique.
 * Elle crée et affiche la fenêtre d'accueil lorsque l'application démarre.
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 * @see AccueilFrame
 */
public class App {

    /**
     * Méthode principale qui démarre l'application en créant la fenêtre d'accueil.
     * Cette méthode est le point d'entrée de l'application.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     * @see Runnable
     */
    public static void main(String[] args) {
        // Crée et affiche la fenêtre d'accueil
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Création de la fenêtre d'accueil avec son contrôleur
                new AccueilFrame(new AccueilFrameControleur()).setVisible(true);
            }
        });
    }
}