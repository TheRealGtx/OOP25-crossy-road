package it.unibo.crossyroad.model.api;

import java.util.Objects;

public record Pair<E1, E2> (E1 e1, E2 e2) { 
    @Override
    public final int hashCode() {
        return Objects.hash(e1, e2);
    }

    @Override
    public final boolean equals(Object arg0) {
        if (this == arg0) {
            return true;
        }
        if (arg0 == null) {
            return false;
        }
        if (getClass() != arg0.getClass()) {
            return false;
        }
        Pair<E1, E2> other = (Pair<E1, E2>) arg0;
        return Objects.equals(e1, other.e1) && Objects.equals(e2, other.e2);
    }
}
