package com.bookislife.flow.data;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/27.
 */
public class BaseModifier {

    public static final String INC = "$inc";
    public static final String SET = "$set";
    public static final String UNSET = "$unset";
    public static final String ADD_TO_SET = "$addToSet";
    public static final String PULL = "$pull";
    public static final String PUSH = "$push";

    private Map<String, Map<String, Object>> modifiers;

    public BaseModifier() {
    }

    public BaseModifier(Map<String, Map<String, Object>> modifiers) {
        this.modifiers = modifiers;
    }

    public Map<String, Object> getModifiers() {
        if (null == modifiers) {
            return null;
        }
        return Collections.unmodifiableMap(modifiers);
    }

    public void modifier(String op, Map<String, Object> newUpdater) {
        Map<String, Object> updater;
        if (modifiers.containsKey(op)) {
            updater = modifiers.get(op);
        } else {
            updater = new LinkedHashMap<>();
        }
        updater.putAll(newUpdater);
        modifiers.put(op, updater);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseModifier{");
        sb.append("modifiers=").append(modifiers);
        sb.append('}');
        return sb.toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Map<String, Object>> modifiers;

        public Builder() {
            modifiers = new LinkedHashMap<>();
        }

        public Builder inc(String column, Number value) {
            addUpdater(INC, column, value);
            return this;
        }

        public Builder set(String column, Object value) {
            addUpdater(SET, column, value);
            return this;
        }

        public Builder unset(String column) {
            addUpdater(UNSET, column, 1);
            return this;
        }

        public Builder push(String column, Object value) {
            addUpdater(PUSH, column, value);
            return this;
        }

        public Builder pull(String column, Object value) {
            addUpdater(PULL, column, value);
            return this;
        }

        public Builder addToSet(String column, Object value) {
            addUpdater(ADD_TO_SET, column, value);
            return this;
        }

        public Builder addUpdater(String op, String column, Object value) {
            Map<String, Object> updater;
            if (modifiers.containsKey(op)) {
                updater = modifiers.get(op);
            } else {
                updater = new LinkedHashMap<>();
            }
            updater.put(column, value);
            modifiers.put(op, updater);
            return this;
        }

        public BaseModifier create() {
            return new BaseModifier(modifiers);
        }
    }
}
