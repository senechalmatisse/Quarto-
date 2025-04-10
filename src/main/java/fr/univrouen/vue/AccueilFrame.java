package fr.univrouen.vue;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import fr.univrouen.controleur.AccueilFrameControleur;

/**
 * Fenêtre d'accueil de l'application Quarto.
 * <p>
 * Elle permet au joueur de choisir une intelligence artificielle (IA) et une stratégie
 * avant de commencer une nouvelle partie. Elle affiche aussi un titre dynamique et 
 * une interface stylisée.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 2.0
 * @since JDK 17
 */
public class AccueilFrame extends JFrame {
    /** Contrôleur associé à la fenêtre d'accueil */
    private AccueilFrameControleur controleur;

    /** Messages alternés dans le titre d'accueil */
    private static final String[] MESSAGES_TITRE = {
        "Bienvenue dans Quarto!",
        "Prêt à jouer ?"
    };

    /**
     * Construit et initialise la fenêtre d'accueil.
     *
     * @param controleur Le contrôleur associé à cette vue.
     */
    public AccueilFrame(AccueilFrameControleur controleur) {
        this.controleur = controleur;

        setTitle("Accueil - Quarto!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = createBackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel labelTitre = creerLabelTitre();
        JPanel boutonsPanel = createBoutonsPanel();

        mainPanel.add(labelTitre, BorderLayout.NORTH);
        mainPanel.add(boutonsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        startTitreRotation(labelTitre);

        pack();
    }

    /**
     * Crée un JPanel avec une image de fond personnalisée.
     *
     * @return Le panneau avec fond personnalisé.
     */
    private JPanel createBackgroundPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageFond = StyleInterface.getImageFond();
                if (imageFond != null) {
                    g.drawImage(imageFond.getImage(), 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
    }

    /**
     * Crée le label principal avec le message d'accueil.
     *
     * @return Le JLabel stylisé.
     */
    private JLabel creerLabelTitre() {
        JLabel titre = new JLabel(MESSAGES_TITRE[0]);
        titre.setFont(StyleInterface.POLICE_TITRE);
        titre.setForeground(StyleInterface.TITRE_COULEUR);
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        return titre;
    }

    /**
     * Crée un bouton stylisé avec une couleur et un événement d'action associé.
     *
     * @param texte   Le texte du bouton.
     * @param couleur La couleur de fond du bouton.
     * @param action  L'action à exécuter au clic.
     * @return Le bouton configuré.
     */
    private JButton createStyledButton(String texte, Color couleur, ActionListener action) {
        JButton bouton = new JButton(texte);
        bouton.setFont(StyleInterface.POLICE_BOUTON);
        bouton.setBackground(couleur);
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
        bouton.setPreferredSize(new Dimension(220, 50));

        bouton.addActionListener(action);
        bouton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { bouton.setBackground(StyleInterface.BOUTON_SURVOL_COULEUR); }
            public void mouseExited(MouseEvent e) { bouton.setBackground(couleur); }
        });

        return bouton;
    }

    /**
     * Crée le panneau contenant les boutons d'action : Jouer et Historique/Règles.
     *
     * @return Le JPanel contenant les boutons.
     */
    private JPanel createBoutonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panel.setOpaque(false);

        JButton boutonJouer = createStyledButton(
                                "Jouer", 
                                StyleInterface.JOUER_BOUTON_COULEUR, e -> controleur.configurationPartie(this)
        );
        JButton boutonRegles = createStyledButton(
                                "Historique et Règles",
                                StyleInterface.HISTOIRE_REGLES_BOUTON_COULEUR, e -> controleur.onHistoriqueButtonClick()
        );

        panel.add(boutonJouer);
        panel.add(boutonRegles);
        return panel;
    }

    /**
     * Lance une animation sur le titre en changeant le message toutes les 2 secondes.
     *
     * @param labelTitre Le JLabel à mettre à jour.
     */
    private void startTitreRotation(JLabel labelTitre) {
        Timer timer = new Timer(2000, new ActionListener() {
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                labelTitre.setText(MESSAGES_TITRE[index]);
                index = (index + 1) % MESSAGES_TITRE.length;
            }
        });
        timer.start();
    }
}