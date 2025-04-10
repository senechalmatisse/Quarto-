package fr.univrouen.controleur;

import java.util.*;
import java.util.function.Supplier;

import javax.swing.*;

import fr.univrouen.modele.jeu.partie.*;
import fr.univrouen.modele.jeu.partie.strategie.*;
import fr.univrouen.modele.joueur.*;
import fr.univrouen.modele.joueur.ia.*;
import fr.univrouen.modele.joueur.humain.JoueurHumain;
import fr.univrouen.vue.jeu.*;

/**
 * Contrôleur de la fenêtre d'accueil.
 * Gère la logique de démarrage du jeu et l'accès à l'historique/règles.
 * 
 * @author Matisse SENECHAL
 * @version 1.1
 * @since JDK 17
 */
public class AccueilFrameControleur {
    /** Mapping entre les types d'IA disponibles et leurs constructeurs. */
    private final Map<IA, Supplier<Joueur>> MAP_IA = new EnumMap<>(IA.class);

    /** Mapping entre les stratégies gagnantes disponibles et leurs constructeurs. */
    private final Map<Strategies, Supplier<StrategieGagnante>> MAP_STRATEGIE = new EnumMap<>(Strategies.class);

    /**
     * Constructeur du contrôleur. Initialise les mappings IA et Stratégies.
     */
    public AccueilFrameControleur() {
        initialiserMappings();
    }

    /**
     * Gère l'événement de clic sur le bouton "Jouer" et lance le jeu.
     * 
     * @param iaChoisie         L'IA sélectionnée par l'utilisateur.
     * @param strategieChoisie  La stratégie gagnante choisie.
     */
    public void onJouerButtonClick(IA iaChoisie, Strategies strategieChoisie) {
        if (iaChoisie == null || strategieChoisie == null) return;

        Joueur joueurHumain = new JoueurHumain();
        Joueur joueurIA = MAP_IA.get(iaChoisie).get();
        StrategieGagnante strategie = MAP_STRATEGIE.get(strategieChoisie).get();

        Jeu jeu = new JeuQuarto(joueurHumain, joueurIA, strategie);
        QuartoFrame frame = new QuartoFrame(jeu);
        frame.setVisible(true);

        if (!jeu.getJoueurActuel().getNom().contains("Humain")) {
            jeu.getJoueurActuel().choisirPiece(jeu, null);
        }
    }

    /**
     * Affiche deux boîtes de dialogue successives pour que le joueur choisisse l’IA et la stratégie.
     * Ensuite, appelle le contrôleur pour démarrer la partie avec les choix sélectionnés.
     *
     * @param parent La fenêtre parente utilisée pour afficher les boîtes de dialogue.
     */
    public void configurationPartie(JFrame parent) {
        IA iaChoisie = (IA) JOptionPane.showInputDialog(
            parent,
            "Contre quelle IA voulez-vous jouer ?",
            "Choisir IA",
            JOptionPane.QUESTION_MESSAGE,
            null,
            IA.values(),
            IA.MINIMAX
        );

        if (iaChoisie == null) return;

        Strategies strategieChoisie = (Strategies) JOptionPane.showInputDialog(
            parent,
            "Quelle stratégie voulez-vous utiliser ?",
            "Choisir Stratégie",
            JOptionPane.QUESTION_MESSAGE,
            null,
            Strategies.values(),
            Strategies.STRATEGIE_NIVEAU_1
        );

        if (strategieChoisie == null) return;

        onJouerButtonClick(iaChoisie, strategieChoisie);
    }

    /**
     * Gère l'événement de clic sur le bouton "Historique / Règles".
     */
    public void onHistoriqueButtonClick() {
        new HistoriqueEtReglesFrame(new HistoriqueEtReglesControleur()).setVisible(true);
    }

    /**
     * Initialise les mappings entre enum (IA et Stratégies) et leurs constructeurs.
     */
    private void initialiserMappings() {
        MAP_IA.put(IA.MINIMAX, JoueurMinimax::new);
        MAP_IA.put(IA.NEGAMAX, JoueurNegamax::new);
        MAP_IA.put(IA.ALPHA_BETA, JoueurAlphaBeta::new);
        MAP_IA.put(IA.NEGA_BETA, JoueurNegaBeta::new);

        MAP_STRATEGIE.put(Strategies.STRATEGIE_NIVEAU_1, StrategieNiveau1::new);
        MAP_STRATEGIE.put(Strategies.STRATEGIE_NIVEAU_2, StrategieNiveau2::new);
        MAP_STRATEGIE.put(Strategies.STRATEGIE_NIVEAU_3, StrategieNiveau3::new);
        MAP_STRATEGIE.put(Strategies.STRATEGIE_NIVEAU_4, StrategieNiveau4::new);
    }
}