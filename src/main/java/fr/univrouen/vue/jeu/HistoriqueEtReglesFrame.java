package fr.univrouen.vue.jeu;

import javax.swing.*;
import java.awt.*;

import fr.univrouen.vue.StyleInterface;
import fr.univrouen.controleur.HistoriqueEtReglesControleur;

/**
 * Fenêtre d'affichage de l'historique et des règles du jeu Quarto.
 * <p>
 * Cette interface graphique présente une vue HTML stylisée avec un fond personnalisé.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.2
 * @since JDK 17
 */
public class HistoriqueEtReglesFrame extends JFrame {

    /**
     * Initialise et affiche la fenêtre d'historique et des règles.
     *
     * @param controleur Contrôleur chargé de fournir les contenus formatés.
     */
    public HistoriqueEtReglesFrame(HistoriqueEtReglesControleur controleur) {
        initialiserFenetre();

        JPanel backgroundPanel = creerPanelAvecFond();
        JEditorPane htmlViewer = creerEditorPane();

        // Chargement du contenu stylisé depuis le contrôleur
        String contenu = controleur.chargerContenuHtml();
        htmlViewer.setText(contenu);        
        htmlViewer.setCaretPosition(0); // Remonter en haut du texte

        JScrollPane scrollPane = new JScrollPane(htmlViewer);
        configurerScrollPane(scrollPane);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    /**
     * Configure la fenêtre principale : titre, taille, comportement de fermeture, centrage.
     */
    private void initialiserFenetre() {
        setTitle("Historique et Règles de Quarto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    /**
     * Crée un panneau de fond avec une image personnalisée.
     * 
     * @return Le panneau prêt à accueillir les composants.
     * @see StyleInterface#getImageFond
     */
    private JPanel creerPanelAvecFond() {
        return new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon fond = StyleInterface.getImageFond();
                if (fond != null) {
                    g.drawImage(fond.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }

    /**
     * Crée un éditeur HTML non éditable pour afficher du texte stylisé.
     *
     * @return Le composant prêt à l'emploi.
     */
    private JEditorPane creerEditorPane() {
        JEditorPane pane = new JEditorPane();
        pane.setContentType("text/html");
        pane.setEditable(false);
        pane.setOpaque(false);
        return pane;
    }

    /**
     * Configure l'apparence transparente du JScrollPane (fond non opaque).
     *
     * @param scrollPane Le panneau de défilement à styliser.
     */
    private void configurerScrollPane(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
    }
}