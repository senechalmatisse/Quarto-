package fr.univrouen.vue.jeu;

import java.awt.*;
import javax.swing.*;

import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.orchestrateur.Orchestrateur;
import fr.univrouen.modele.jeu.partie.observer.Observer;
import fr.univrouen.vue.StyleInterface;

/**
 * Fenêtre principale de l'application Quarto, contenant le plateau, les pièces restantes
 * et l'affichage dynamique du joueur courant.
 * <p>
 * Une image de fond personnalisée est utilisée en arrière-plan.
 * </p>
 *  
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 * @see JFrame
 * @see Observer
 * @see Jeu
 * @see ImageIcon
 * @see StyleInterface#getImageFond()
 * @see PlateauPanel
 * @see PiecesRestantesPanel
 */
public class QuartoFrame extends JFrame {
    /** Image de fond utilisée pour la fenêtre principale. */
    private ImageIcon imageFond = StyleInterface.getImageFond();

    /** Étiquette personnalisée affichant le joueur courant. */
    private JoueurCourantLabel joueurCourantLabel;

    /**
     * Construit et initialise la fenêtre principale du jeu.
     *
     * @param jeu Le modèle de jeu utilisé dans cette partie.
     */
    public QuartoFrame(Jeu jeu) {
        super("Quarto");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initialiserInterface(jeu);

        pack();
        setLocationRelativeTo(null); // Centrage
        setVisible(true);
    }

    /**
     * Initialise tous les composants graphiques et les place dans la fenêtre.
     *
     * @param jeu L’instance du jeu à observer.
     */
    private void initialiserInterface(Jeu jeu) {
        // Création du panneau avec fond personnalisé
        JPanel conteneurPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imageFond != null) {
                    g.drawImage(imageFond.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Création des composants du jeu
        Orchestrateur orchestrateur = new Orchestrateur(jeu);
        PiecesRestantesPanel panneauPieces = new PiecesRestantesPanel(jeu);
        PlateauPanel panneauPlateau = new PlateauPanel(jeu, panneauPieces);
        joueurCourantLabel = new JoueurCourantLabel(jeu);

        // Abonnement des observateurs
        jeu.addObserver(orchestrateur);
        jeu.addObserver(panneauPlateau);
        jeu.addObserver(panneauPieces);
        jeu.addObserver(joueurCourantLabel);

        // Ajout des composants à la fenêtre
        conteneurPrincipal.add(panneauPlateau, BorderLayout.WEST);
        conteneurPrincipal.add(panneauPieces, BorderLayout.EAST);
        conteneurPrincipal.add(joueurCourantLabel, BorderLayout.NORTH);

        setContentPane(conteneurPrincipal);
    }
}