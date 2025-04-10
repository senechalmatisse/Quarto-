package fr.univrouen.controleur;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Contrôleur chargé de gérer le chargement et le formatage des contenus texte et HTML
 * pour la vue d'historique et de règles du jeu Quarto.
 * <p>
 * Il lit les ressources depuis le classpath (dossier {@code resources}) et assemble une page HTML complète.
 * </p>
 * 
 * @author Matisse SENECHAL
 * @version 1.2
 * @since JDK 17
 */
public class HistoriqueEtReglesControleur {

    /**
     * Charge le contenu d’un fichier texte situé dans les ressources du projet.
     *
     * @param chemin Le chemin relatif dans le dossier {@code resources}.
     * @return Le contenu du fichier sous forme de chaîne, ou une chaîne vide si une erreur survient.
     */
    public String chargerFichier(String chemin) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(chemin)) {
            if (inputStream == null) {
                return "";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder contenu = new StringBuilder();
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append('\n');
            }

            return contenu.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Formate le contenu HTML complet en injectant le CSS, l’histoire et les règles.
     *
     * @param style    Le code CSS.
     * @param histoire Le texte historique du jeu.
     * @param regles   Le texte des règles du jeu.
     * @return Une page HTML complète prête à être affichée dans un composant HTML.
     */
    public String formaterContenu(String style, String histoire, String regles) {
        return new StringBuilder()
            .append("<!DOCTYPE html>\n<html lang='fr'>\n<head>\n")
            .append("<meta charset='UTF-8'>\n")
            .append("<meta name='viewport' content='width=device-width'>\n")
            .append("<title>Historique et Règles du jeu Quarto</title>\n")
            .append("<style>\n").append(style).append("\n</style>\n")
            .append("</head>\n<body>\n")
            .append(histoire).append("\n<hr>\n").append(regles)
            .append("\n</body>\n</html>")
            .toString();
    }

    /**
     * Charge et assemble le contenu HTML complet à afficher dans la vue.
     *
     * @return Une page HTML contenant le style, l’histoire et les règles du jeu.
     */
    public String chargerContenuHtml() {
        String style = chargerFichier("style/historiqueRegles.css");
        String histoire = chargerFichier("fragment/histoire.txt");
        String regles = chargerFichier("fragment/regles.txt");

        return formaterContenu(style, histoire, regles);
    }   
}