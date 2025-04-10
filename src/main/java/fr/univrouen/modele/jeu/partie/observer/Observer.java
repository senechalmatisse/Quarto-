package fr.univrouen.modele.jeu.partie.observer;

/**
 * Interface représentant un observateur dans le patron de conception Observateur (Observer pattern).
 * <p>
 * Elle définit la méthode {@code update()} qui est appelée automatiquement lorsqu’un objet {@link Observable}
 * auquel l’observateur est abonné émet une notification de changement d’état.
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 * @see Observable
 */
public interface Observer {

    /**
     * Méthode appelée lorsqu’un objet observé notifie un changement.
     * <p>
     * Elle permet à l’observateur de se mettre à jour selon l’état courant de l’objet observé.
     * </p>
     */
    void update();
}