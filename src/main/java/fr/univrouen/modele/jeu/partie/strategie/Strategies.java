package fr.univrouen.modele.jeu.partie.strategie;

/**
 * Énumération des différents niveaux de stratégie gagnante dans le jeu Quarto.
 * <p>
 * Chaque stratégie représente une méthode différente de détection de victoire,
 * allant de la plus simple (alignements classiques) à des configurations plus complexes
 * (carrés tournants, carrés 2x2, carrés 3x3).
 * </p>
 *
 * <ul>
 *   <li>{@link #STRATEGIE_NIVEAU_1} : Détection d'un alignement horizontal, vertical ou diagonal de 4 pièces ayant une caractéristique commune.</li>
 *   <li>{@link #STRATEGIE_NIVEAU_2} : Détection des alignements du niveau 1 + détection de petits carrés (2x2) de pièces ayant une caractéristique commune.</li>
 *   <li>{@link #STRATEGIE_NIVEAU_3} : Inclut les niveaux précédents + détection de grands carrés (3x3) dont les 4 coins partagent une caractéristique commune.</li>
 *   <li>{@link #STRATEGIE_NIVEAU_4} : Inclut tous les niveaux précédents + détection de carrés tournants (formes en rotation, ex. : a2, b1, c2, b3).</li>
 * </ul>
 *
 * <p>Ces stratégies sont principalement utilisées pour déterminer si une combinaison gagnante est présente sur le plateau.</p>
 *
 * @author Matisse SENECHAL
 * @version 1.0
 * @since JDK 17
 * @see StrategieAbstraite
 * @see StrategieNiveau1
 * @see StrategieNiveau2
 * @see StrategieNiveau3
 * @see StrategieNiveau4
 */
public enum Strategies {
    /** Niveau 1 : alignements classiques (lignes, colonnes, diagonales) */
    STRATEGIE_NIVEAU_1,

    /** Niveau 2 : alignements du niveau 1 + carrés 2x2 */
    STRATEGIE_NIVEAU_2,

    /** Niveau 3 : niveaux précédents + carrés 3x3 (coins) */
    STRATEGIE_NIVEAU_3,

    /** Niveau 4 : niveaux précédents + carrés tournants */
    STRATEGIE_NIVEAU_4
}