package model;

import java.util.Collection;
import java.util.Collections;

/**
 * Représente le résultat de la révélation des champs dans le jeu Démineur.
 * Cette classe contient une collection des champs révélés et un état décrivant le résultat de la révélation.
 */
public class RevealFieldsResult {

    /**
     * Énumération représentant l'état d'une révélation de champ.
     * - FIELD_NOT_REVEALED : Aucun champ n'a été révélé.
     * - FIELDS_REVEALED : Des champs ont été révélés sans avoir trouvé de mine.
     * - FOUND_MINE : Une mine a été trouvée lors de la révélation des champs.
     */
    public enum RevealFieldState {
        FIELD_NOT_REVEALED,  // Aucun champ n'a été révélé
        FIELDS_REVEALED,     // Des champs ont été révélés sans avoir trouvé de mine
        FOUND_MINE           // Une mine a été trouvée lors de la révélation
    }

    // Collection des champs révélés
    private Collection<Field> revealedFields;

    /**
     * Constructeur par défaut qui initialise la collection de champs révélés comme étant vide.
     */
    public RevealFieldsResult() {
        this.revealedFields = Collections.emptyList();  // Aucune case révélée au départ
    }

    /**
     * Constructeur qui initialise la collection de champs révélés avec une collection donnée.
     *
     * @param fields La collection des champs révélés.
     */
    public RevealFieldsResult(Collection<Field> fields) {
        this.revealedFields = fields;
    }

    /**
     * Retourne la collection des champs révélés.
     * Cette collection est en lecture seule.
     *
     * @return La collection des champs révélés.
     */
    public Collection<Field> getRevealedFields() {
        return Collections.unmodifiableCollection(revealedFields);
    }

    /**
     * Retourne l'état de la révélation des champs :
     * - FIELD_NOT_REVEALED : Aucun champ n'a été révélé.
     * - FIELDS_REVEALED : Des champs ont été révélés sans avoir trouvé de mine.
     * - FOUND_MINE : Une mine a été trouvée lors de la révélation.
     *
     * @return L'état de la révélation des champs.
     */
    public RevealFieldState getState() {
        // Si aucun champ n'a été révélé
        if (revealedFields.isEmpty()) {
            return RevealFieldState.FIELD_NOT_REVEALED;
        }
        // Vérifier si un des champs révélés contient une mine
        for (Field field : revealedFields) {
            if (field.hasMine()) {
                return RevealFieldState.FOUND_MINE;  // Mine trouvée
            }
        }
        return RevealFieldState.FIELDS_REVEALED;  // Aucun mine trouvée, champs révélés avec succès
    }
}
