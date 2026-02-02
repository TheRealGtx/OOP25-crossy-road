package it.unibo.crossyroad.model.api;

import java.util.Objects;

/**
 * Represents a pair.
 * 
 * @param <T> type of the first element.
 * 
 * @param <U> type of the second element.
 * 
 * @param e1 first element.
 * 
 * @param e2 second element.
 */
public record Pair<T, U>(T e1, U e2) { 
    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }

    @Override
    public boolean equals(final Object arg0) {
        if (this == arg0) {
            return true;
        }
        if (arg0 == null) {
            return false;
        }
        if (getClass() != arg0.getClass()) {
            return false;
        }
        final Pair<T, U> other = (Pair<T, U>) arg0;
        return Objects.equals(e1, other.e1) && Objects.equals(e2, other.e2);
    }
}
