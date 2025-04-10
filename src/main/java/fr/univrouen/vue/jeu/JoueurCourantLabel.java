package fr.univrouen.vue.jeu;

import javax.swing.*;
import java.awt.*;

import fr.univrouen.controleur.util.JoueurControleur;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.observer.Observer;

/**
 * Composant graphique affichant dynamiquement le joueur courant avec halo coloré, icône
 * et animation de transition. Le fond et le texte changent de couleur selon si le joueur est
 * humain ou contrôlé par l'IA.
 * 
 * 🟦 Humain : fond bleu clair  
 * 🟥 IA     : fond rouge clair
 * 
 * @author Matisse SENECHAL
 * @version 3.2
 * @since JDK 17
 */
public class JoueurCourantLabel extends JPanel implements Observer {
    /** Durée de l’animation en nombre d’étapes. */
    private static final int NB_ETAPES_ANIMATION = 20;

    /** Délai entre chaque frame de l’animation (en ms). */
    private static final int DELAI_ANIMATION_MS = 15;

    /** Rayon d'arrondi des coins du fond. */
    private static final int RAYON_ARRONDI = 20;

    /** Taille des icônes de joueur. */
    private static final int TAILLE_ICONE = 24;

    /** Couleur de texte pour un joueur humain. */
    private static final Color COULEUR_TEXTE_HUMAIN = new Color(52, 152, 219);

    /** Couleur de texte pour une IA. */
    private static final Color COULEUR_TEXTE_IA = new Color(231, 76, 60);

    /** Couleur de fond pour un joueur humain. */
    private static final Color COULEUR_FOND_HUMAIN = new Color(52, 152, 219, 50);

    /** Couleur de fond pour une IA. */
    private static final Color COULEUR_FOND_IA = new Color(231, 76, 60, 50);

    /** Chemin vers l’icône du joueur humain. */
    private static final String CHEMIN_ICONE_HUMAIN = "img/icons/user.png";

    /** Chemin vers l’icône de l’IA. */
    private static final String CHEMIN_ICONE_IA = "img/icons/ia.png";

    /** Composant d’affichage du nom du joueur courant. */
    private final JLabel etiquetteNomJoueur = new JLabel();

    /** Référence vers l’objet métier du jeu. */
    private final Jeu jeu;

    /** Couleur de fond actuellement affichée. */
    private Color couleurFondActuelle = COULEUR_FOND_HUMAIN;

    /** Couleur de fond cible vers laquelle effectuer la transition. */
    private Color couleurFondCible;

    /** Opacité actuelle du texte (entre 0.0 et 1.0). */
    private float opaciteTexte = 0f;

    /** 
     * Timer pour animer la transition de fond.
     * Timer pour animer le fondu du texte.
     */
    private Timer minuteurAnimationFond, minuteurAnimationTexte;

    /**
     * Initialise le panneau d'affichage du joueur courant avec ses styles et observe le jeu.
     *
     * @param jeu L’instance du jeu à observer.
     */
    public JoueurCourantLabel(Jeu jeu) {
        this.jeu = jeu;

        setLayout(new GridBagLayout());
        setOpaque(false);

        etiquetteNomJoueur.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetteNomJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetteNomJoueur.setForeground(new Color(0, 0, 0, 0)); // Invisible au départ
        add(etiquetteNomJoueur);

        update(); // Mise à jour initiale
    }

    @Override
    public void update() {
        boolean joueurEstHumain = JoueurControleur.estHumain(jeu.getJoueurActuel());

        couleurFondCible = joueurEstHumain ? COULEUR_FOND_HUMAIN : COULEUR_FOND_IA;
        etiquetteNomJoueur.setForeground(joueurEstHumain ? COULEUR_TEXTE_HUMAIN : COULEUR_TEXTE_IA);
        etiquetteNomJoueur.setText(jeu.getJoueurActuel().toString());
        etiquetteNomJoueur.setIcon(chargerIconeJoueur(joueurEstHumain));
        etiquetteNomJoueur.setIconTextGap(10);

        lancerAnimationFond();
        lancerAnimationTexte();
    }

    /**
     * Lance une animation de transition fluide entre la couleur de fond actuelle et la couleur cible.
     */
    private void lancerAnimationFond() {
        if (minuteurAnimationFond != null && minuteurAnimationFond.isRunning()) {
            minuteurAnimationFond.stop();
        }

        final int[] etape = {0};
        final Color debut = couleurFondActuelle;
        final Color cible = couleurFondCible;

        minuteurAnimationFond = new Timer(DELAI_ANIMATION_MS, e -> {
            float ratio = (float) etape[0] / NB_ETAPES_ANIMATION;
            couleurFondActuelle = interpolerCouleur(debut, cible, ratio);
            repaint();

            if (++etape[0] > NB_ETAPES_ANIMATION) {
                couleurFondActuelle = cible;
                minuteurAnimationFond.stop();
            }
        });

        minuteurAnimationFond.start();
    }

    /**
     * Lance une animation de fondu pour faire apparaître progressivement le texte.
     */
    private void lancerAnimationTexte() {
        if (minuteurAnimationTexte != null && minuteurAnimationTexte.isRunning()) {
            minuteurAnimationTexte.stop();
        }

        opaciteTexte = 0f;
        final int[] etape = {0};

        minuteurAnimationTexte = new Timer(DELAI_ANIMATION_MS, e -> {
            opaciteTexte = Math.min(1f, (float) etape[0] / NB_ETAPES_ANIMATION);
            etiquetteNomJoueur.setForeground(ajusterAlpha(etiquetteNomJoueur.getForeground(), opaciteTexte));
            repaint();

            if (++etape[0] > NB_ETAPES_ANIMATION) {
                minuteurAnimationTexte.stop();
            }
        });

        minuteurAnimationTexte.start();
    }

    /**
     * Interpole linéairement entre deux couleurs.
     *
     * @param c1    La couleur de départ.
     * @param c2    La couleur cible.
     * @param ratio Le ratio entre 0.0 (c1) et 1.0 (c2).
     * @return      La couleur interpolée.
     */
    private Color interpolerCouleur(Color c1, Color c2, float ratio) {
        int r = (int) (c1.getRed()   + (c2.getRed()   - c1.getRed())   * ratio);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * ratio);
        int b = (int) (c1.getBlue()  + (c2.getBlue()  - c1.getBlue())  * ratio);
        int a = (int) (c1.getAlpha() + (c2.getAlpha() - c1.getAlpha()) * ratio);
        return new Color(r, g, b, a);
    }

    /**
     * Applique une transparence à une couleur existante.
     *
     * @param couleur     La couleur d'origine.
     * @param alphaRatio  Ratio de transparence entre 0.0 et 1.0.
     * @return            La couleur avec alpha ajusté.
     */
    private Color ajusterAlpha(Color couleur, float alphaRatio) {
        return new Color(
            couleur.getRed(),
            couleur.getGreen(),
            couleur.getBlue(),
            Math.min(255, (int) (alphaRatio * 255))
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (couleurFondActuelle != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(couleurFondActuelle);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), RAYON_ARRONDI, RAYON_ARRONDI);
            g2.dispose();
        }
    }

    /**
     * Charge et redimensionne l’icône correspondant au joueur (humain ou IA).
     *
     * @param estHumain Vrai si le joueur est humain, faux s’il s’agit d’une IA.
     * @return          Une ImageIcon redimensionnée, ou null si la ressource est introuvable.
     */
    private ImageIcon chargerIconeJoueur(boolean estHumain) {
        String chemin = estHumain ? CHEMIN_ICONE_HUMAIN : CHEMIN_ICONE_IA;
        java.net.URL url = getClass().getClassLoader().getResource(chemin);

        if (url != null) {
            Image image = new ImageIcon(url).getImage().getScaledInstance(TAILLE_ICONE, TAILLE_ICONE, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            return null;
        }
    }
}