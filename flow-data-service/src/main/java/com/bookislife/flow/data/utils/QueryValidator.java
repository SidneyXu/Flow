package com.bookislife.flow.data.utils;

import com.bookislife.flow.core.utils.ObjectTraverser;
import com.bookislife.flow.data.Condition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bookislife.flow.data.Condition.*;

/**
 * Created by SidneyXu on 2016/06/17.
 */
public class QueryValidator {

    private static final List<String> ops = Arrays.asList(
            EQUAL_TO,
            NOT_EQUAL_TO,
            IN,
            NOT_IN,
            EXISTS,
            OR,
            LIKE,
            LINK,
            LOWER_THAN,
            LOWER_THAN_OR_EQUAL_TO,
            GREATER_THAN,
            GREATER_THAN_OR_EQUAL_TO
    );

    public static void validate(Condition condition) {
        if (condition == null) return;
        Map<String, Object> where = condition.getWhere();
        new ObjectTraverser<Map<String, Object>>(where) {
            @Override
            public boolean visit(Object object) {
                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.size() == 1) {
                        String key = (String) map.keySet().iterator().next();
                        if (key.startsWith("$") && !ops.contains(key)) {
                            throw new IllegalArgumentException("Query contains invalidate operator: " + key);
                        }
                    }
                }
                return true;
            }
        }.transve();
    }
}
