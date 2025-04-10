package fr.univrouen.modele.orchestrateur;

import javax.swing.Timer;

import fr.univrouen.modele.joueur.Joueur;
import fr.univrouen.modele.jeu.piece.Piece;
import fr.univrouen.modele.jeu.partie.Jeu;
import fr.univrouen.modele.jeu.partie.observer.Observer;

/**
 * L’orchestrateur du jeu Quarto.
 * <p>
 * Cette classe agit comme un gestionnaire automatique de tours pour les joueurs non humains (IA).
 * Lorsqu’un changement d’état est détecté via {@link #update()}, l’orchestrateur déclenche automatiquement
 * le tour de l’IA après un léger délai, simulant un comportement réaliste.
 * </p>
 * 
 * <p>Elle permet également de déléguer l’exécution logique d’un tour à l’IA via {@link #jouerTour()}.</p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class Orchestrateur implements Observer {
    /** Instance du jeu à superviser. */
    private Jeu jeu;

    /**
     * Crée un orchestrateur supervisant une instance du jeu.
     * 
     * @param jeu L’instance du jeu à suivre.
     */
    public Orchestrateur(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void update() {
        if (jeu.estTerminee()) return;
    
        Joueur joueurActuel = jeu.getJoueurActuel();
        Piece pieceCourante = jeu.getPieceCourante();
    
        boolean estIA = joueurActuel != null && !joueurActuel.getNom().contains("Humain");
    
        if (estIA && pieceCourante != null) {
            Timer timer = new Timer(300, e -> {
                if (!jeu.estTerminee() && !jeu.getJoueurActuel().getNom().contains("Humain")) {
                    jouerTour();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    

    /**
     * Joue automatiquement le tour du joueur actuel si c’est une IA.
     * <p>
     * Si une pièce est en attente de placement, elle est posée via {@code placerPiece}.
     * Sinon, une nouvelle pièce est choisie à donner à l’adversaire via {@code choisirPiece}.
     * </p>
     */
    public void jouerTour() {
        if (jeu.estTerminee()) return;
    
        Joueur joueurActuel = jeu.getJoueurActuel();
        if (joueurActuel.getNom().contains("Humain")) return;
    
        Piece pieceCourante = jeu.getPieceCourante();
    
        if (pieceCourante != null) {
            joueurActuel.placerPiece(jeu, pieceCourante, null);
        } else {
            joueurActuel.choisirPiece(jeu, null);
        }
    }    
}