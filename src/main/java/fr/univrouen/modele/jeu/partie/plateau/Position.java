package fr.univrouen.modele.jeu.partie.plateau;

/**
 * Représente une position sur un plateau 4x4 dans le jeu Quarto.
 * <p>
 * Chaque position est définie par deux coordonnées entières : ligne ({@code x}) et colonne ({@code y}),
 * comprises entre 0 et 3 inclus.
 * </p>
 * 
 * <p>Cette classe est utilisée pour placer ou interroger les pièces sur le plateau.</p>
 * 
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 */
public class Position {
    /** Les coordonnées de la position. */
    private int x, y;

    /**
     * Constructeur pour créer une position à partir des coordonnées données.
     *
     * @param x La ligne (valeur entre 0 et 3).
     * @param y La colonne (valeur entre 0 et 3).
     * @throws IllegalArgumentException si les coordonnées sont hors limites (hors de 0 à 3).
     */
    public Position(int x, int y) {
        if (x < 0 || x >= 4 || y < 0 || y >= 4) {
            throw new IllegalArgumentException("Les coordonnées doivent être entre 0 et 3.");
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Renvoie la coordonnée en ligne.
     *
     * @return La valeur de {@code x}.
     */
    public int getX() {
        return x;
    }

    /**
     * Définit une nouvelle valeur pour la coordonnée en ligne.
     *
     * @param newX La nouvelle valeur de {@code x}.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Renvoie la coordonnée en colonne.
     *
     * @return La valeur de {@code y}.
     */
    public int getY() {
        return y;
    }

    /**
     * Définit une nouvelle valeur pour la coordonnée en colonne.
     *
     * @param newY La nouvelle valeur de {@code y}.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }
}