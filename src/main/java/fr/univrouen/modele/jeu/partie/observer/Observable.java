package fr.univrouen.modele.jeu.partie.observer;

/**
 * Interface représentant un sujet observable dans le patron de conception Observateur (Observer pattern).
 * <p>
 * Elle permet à des objets {@link Observer} de s’abonner, de se désabonner et d’être notifiés
 * lorsqu’un changement d’état survient.
 * </p>
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 * @see Observer
 */
public interface Observable {

    /**
     * Ajoute un observateur à la liste des observateurs abonnés à cet objet.
     *
     * @param observer L’observateur à enregistrer.
     */
    void addObserver(Observer observer);

    /**
     * Retire un observateur de la liste des observateurs abonnés à cet objet.
     *
     * @param observer L’observateur à désabonner.
     */
    void removeObserver(Observer observer);

    /**
     * Notifie tous les observateurs actuellement abonnés que l’état de l’objet a changé.
     * <p>
     * Appelle la méthode {@code update()} sur chacun des observateurs enregistrés.
     * </p>
     */
    void notifyObservers();
}