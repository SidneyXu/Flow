package com.bookislife.flow.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by SidneyXu on 2016/05/27.
 */
public class Projection {

    private Set<String> selects;

    public Projection() {
        selects = new HashSet<>();
    }

    public Projection(Set<String> selects) {
        this.selects = selects;
    }

    public Set<String> getSelects() {
        return Collections.unmodifiableSet(selects);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Set<String> selects = new HashSet<>();

        public Builder select(String field) {
            selects.add(field);
            return this;
        }

        public Projection create() {
            return new Projection(selects);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Projection{");
        sb.append("selects=").append(selects);
        sb.append('}');
        return sb.toString();
    }
}
