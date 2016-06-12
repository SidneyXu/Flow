package com.bookislife.flow.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class MongoQuery extends BaseQuery {

    public MongoQuery(String tableName) {
        super(tableName);
    }

    @JsonCreator
    public MongoQuery(@JsonProperty("tableName") String tableName,
                      @JsonProperty("condition") Condition condition,
                      @JsonProperty("constraint") Constraint constraint) {
        super(tableName);
        setCondition(condition);
        setConstraint(constraint);
    }

    public Map<String, Object> getQuery() {
        return getCondition().getWhere();
    }
}
