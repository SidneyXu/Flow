package com.bookislife.flow.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/12.
 */
public class Condition {

    public static final String EQUAL_TO = "$eq";
    public static final String NOT_EQUAL_TO = "$ne";
    public static final String IN = "$in";
    public static final String NOT_IN = "$nin";
    public static final String EXISTS = "$exists";
    public static final String OR = "$or";
    public static final String LIKE = "$like";
    public static final String LINK = "$link";
    public static final String LOWER_THAN = "$lt";
    public static final String GREATER_THAN = "$gt";
    public static final String LOWER_THAN_OR_EQUAL_TO = "$lte";
    public static final String GREATER_THAN_OR_EQUAL_TO = "$gte";

    private Map<String, Map<String, Object>> where = new HashMap<>();

    private Condition(Map<String, Map<String, Object>> where) {
        this.where = where;
    }

    public Condition() {
    }

    public Map<String, Object> getWhere() {
        return Collections.unmodifiableMap(where);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Condition{");
        sb.append("where=").append(where);
        sb.append('}');
        return sb.toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Link {
        private String refTable;
        private String refColumn;

        public Link(String targetTable, String targetColumn) {
            this.refTable = targetTable;
            this.refColumn = targetColumn;
        }

        public String getRefTable() {
            return refTable;
        }

        public String getRefColumn() {
            return refColumn;
        }
    }

    public static class Builder {

        // column {op, value}
        private Map<String, Map<String, Object>> where = new HashMap<>();

        public Builder eq(String column, Object value) {
            addCondition(EQUAL_TO, column, value);
            return this;
        }

        public Builder ne(String column, Object value) {
            addCondition(NOT_EQUAL_TO, column, value);
            return this;
        }

        public Builder in(String column, Collection<?> value) {
            addCondition(IN, column, value);
            return this;
        }

        public Builder nin(String column, Collection<?> value) {
            addCondition(NOT_IN, column, value);
            return this;
        }

        public Builder exists(String column, boolean value) {
            addCondition(EXISTS, column, value);
            return this;
        }

        public Builder lt(String column, Object value) {
            addCondition(LOWER_THAN, column, value);
            return this;
        }

        public Builder lte(String column, Object value) {
            addCondition(LOWER_THAN_OR_EQUAL_TO, column, value);
            return this;
        }

        public Builder gt(String column, Object value) {
            addCondition(GREATER_THAN, column, value);
            return this;
        }

        public Builder gte(String column, Object value) {
            addCondition(GREATER_THAN_OR_EQUAL_TO, column, value);
            return this;
        }

        public Builder like(String column, String regex) {
            addCondition(LIKE, column, regex);
            return this;
        }

        public Builder link(String column, Link link) {
            addCondition(LIKE, column, link);
            return this;
        }

        public Builder link(String column, String refTable, String refColumn) {
            addCondition(LIKE, column, new Link(refTable, refColumn));
            return this;
        }

        public Builder addCondition(String op, String column, Object value) {
            Map<String, Object> cond;
            if (where.containsKey(column)) {
                cond = where.get(column);
            } else {
                cond = new HashMap<>();
            }
            cond.put(op, value);
            where.put(column, cond);
            return this;
        }

        public Condition create() {
            return new Condition(where);
        }
    }
}
