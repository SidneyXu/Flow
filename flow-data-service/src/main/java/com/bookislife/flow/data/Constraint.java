package com.bookislife.flow.data;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * Created by SidneyXu on 2016/05/12.
 */
public class Constraint {

    private int limit;
    private int skip;
    private List<String> includes;
    private String sort;

    public Constraint() {
    }

    Constraint(int limit, int skip, List<String> includes, Set<String> sorts) {
        this.limit = limit;
        this.skip = skip;
        this.includes = includes;
        if (sorts != null) {
            this.sort = Joiner.on(',').join(sorts);
        }
    }

    public int getLimit() {
        return limit;
    }

    public int getSkip() {
        return skip;
    }

    public List<String> getIncludes() {
        if (null == includes) {
            return null;
        }
        return Collections.unmodifiableList(includes);
    }

    public String getSort() {
        return sort;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Constraint{");
        sb.append("limit=").append(limit);
        sb.append(", skip=").append(skip);
        sb.append(", includes=").append(includes);
        sb.append(", sort='").append(sort).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int limit;
        private int skip;
        private List<String> includes;
        private Set<String> sorts;

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder skip(int skip) {
            this.skip = skip;
            return this;
        }

        public Builder include(String include) {
            if (includes == null) {
                includes = new ArrayList<>();
            }
            includes.add(include);
            return this;
        }

        public Builder includes(List<String> includes) {
            this.includes = includes;
            return this;
        }

        public Builder sort(String field, boolean asc) {
            if (sorts == null) {
                sorts = new LinkedHashSet<>();
            }
            sorts.add((asc ? "+" : "-") + field);
            return this;
        }

        public Constraint create() {
            return new Constraint(limit, skip, includes, sorts);
        }
    }
}
