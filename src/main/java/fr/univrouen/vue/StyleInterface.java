package fr.univrouen.vue;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import fr.univrouen.App;

/**
 * Gère les styles graphiques globaux de l'application Quarto.
 * <p>
 * Fournit des constantes pour les couleurs, polices, et outils utilitaires pour styliser
 * les composants Swing (boutons, fonds, etc.).
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class StyleInterface {
    /** Couleur du titre */
    protected static final Color TITRE_COULEUR = new Color(245, 245, 220);

    /** Couleur du bouton JOUER */
    protected static final Color JOUER_BOUTON_COULEUR = new Color(34, 193, 195);

    /** Couleur du bouton HISTOIRE et RÈGLES */
    protected static final Color HISTOIRE_REGLES_BOUTON_COULEUR = new Color(255, 87, 34);

    /** Couleur des boutons lors du survol */
    protected static final Color BOUTON_SURVOL_COULEUR = new Color(0, 128, 128);

    /** Police du titre */
    protected static final Font POLICE_TITRE = chargerPolice("police/Montserrat-Regular.ttf", 30f);

    /** Police des boutons */
    protected static final Font POLICE_BOUTON = new Font("Arial", Font.PLAIN, 18);

    /** Définition de l'image de fond */
    private static final String CHEMIN_IMAGE_FOND = "img/plateau/case_foncee.png"; // Chemin de l'image de fond

    /**
     * Retourne l’image de fond utilisée dans l’application.
     *
     * @return L’image de fond ou null si non trouvée.
     */
    public static ImageIcon getImageFond() {
        URL imageFondURL = StyleInterface.class.getClassLoader().getResource(CHEMIN_IMAGE_FOND);
        if (imageFondURL != null) {
            return new ImageIcon(imageFondURL);
        } else {
            return null;
        }
    }

    /**
     * Charge une police personnalisée depuis les ressources du projet.
     *
     * @param cheminRelatif Chemin relatif vers la police.
     * @param taille        Taille de la police.
     * @return La police chargée ou la police par défaut en cas d'erreur.
     */
    private static Font chargerPolice(String cheminRelatif, float taille) {
        try (InputStream stream = App.class.getClassLoader().getResourceAsStream(cheminRelatif)) {
            if (stream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(taille);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return new Font("Arial", Font.PLAIN, (int) taille);
    }
}