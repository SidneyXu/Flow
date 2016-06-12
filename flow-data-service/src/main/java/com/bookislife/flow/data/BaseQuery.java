package com.bookislife.flow.data;

/**
 * Created by SidneyXu on 2016/05/05.
 */
public class BaseQuery {

    public static final String FIELD_TABLE_NAME = "tableName";
    public static final String FIELD_CONDITION = "condition";
    public static final String FIELD_CONSTRAINT = "constraint";

    private final String tableName;
    private Condition condition;
    private Constraint constraint;
    private Projection projection;

    public BaseQuery(String tableName) {
        this.tableName = tableName;
    }

    public static BaseQuery from(String tableName) {
        return new BaseQuery(tableName);
    }

    public Condition.Builder newCondition() {
        return Condition.newBuilder();
    }

    public Constraint.Builder newConstraint() {
        return Constraint.newBuilder();
    }

    public Projection.Builder newProjection() {
        return Projection.newBuilder();
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    public Condition getCondition() {
        return condition;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public String getTableName() {
        return tableName;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseQuery{");
        sb.append("tableName='").append(tableName).append('\'');
        sb.append(", condition=").append(condition);
        sb.append(", constraint=").append(constraint);
        sb.append(", projection=").append(projection);
        sb.append('}');
        return sb.toString();
    }
}
