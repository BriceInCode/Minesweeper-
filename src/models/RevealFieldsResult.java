package models;

import java.util.Collection;
import java.util.Collections;

public class RevealFieldsResult {
    public enum RevealFieldState {
        FIELD_NOT_REVEALED,
        FIELDS_REVEALED,
        FOUND_MINE
    }

    private Collection<Field> revealedFields;
    private RevealFieldState state;

    public RevealFieldsResult() {
        this.revealedFields = Collections.emptyList();
        this.state = RevealFieldState.FIELD_NOT_REVEALED;
    }

    public RevealFieldsResult(Collection<Field> fields) {
        this.revealedFields = Collections.unmodifiableCollection(fields);
        this.state = determineState(fields);
    }

    public RevealFieldsResult(Collection<Field> fields, RevealFieldState state) {
        this.revealedFields = Collections.unmodifiableCollection(fields);
        this.state = state;
    }

    public Collection<Field> getRevealedFields() {
        return revealedFields;
    }

    public RevealFieldState getState() {
        return state;
    }

    private RevealFieldState determineState(Collection<Field> fields) {
        if (fields.isEmpty()) {
            return RevealFieldState.FIELD_NOT_REVEALED;
        }
        for (Field field : fields) {
            if (field.hasMine()) {
                return RevealFieldState.FOUND_MINE;
            }
        }
        return RevealFieldState.FIELDS_REVEALED;
    }
}
