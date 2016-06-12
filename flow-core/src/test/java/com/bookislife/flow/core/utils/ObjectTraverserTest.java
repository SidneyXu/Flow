package com.bookislife.flow.core.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/06/11.
 */
public class ObjectTraverserTest {

    private int count = 0;

    @Test
    public void test01() {
        Map map01 = new HashMap<>();
        Map map02 = new HashMap<>();
        Map map03 = new HashMap<>();
        map01.put("x", 1);
        map01.put("y", 10);
        map02.put("x", 2);
        map02.put("y", 20);
        map02.put("z", "foobar");
        map03.put("z", "java");
        map01.put("map", map02);
        map02.put("map", map03);
        List list01 = new ArrayList<>();
        list01.add(map01);
        Map root = new HashMap<>();
        root.put("node01", list01);
        new ObjectTraverser<Map>(root) {

            @Override
            public boolean visit(Object object) {
                System.out.println("------loop" + (++count) + " time------ at " + object);

                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.containsKey("z")) {
                        System.out.println(map.get("z"));
                        return false;
                    }
                }
                return true;
            }
        }.transve();
    }

    @Test
    public void test02() {
        Map map01 = new HashMap<>();
        Map map02 = new HashMap<>();
        map01.put("x", 1);
        map01.put("y", 10);
        map02.put("x", 2);
        map02.put("y", 20);
        map02.put("z", "foobar");
        map01.put("map", map02);
        List list01 = new ArrayList<>();
        list01.add(map01);
        new ObjectTraverser<List>(list01) {

            @Override
            public boolean visit(Object object) {
                System.out.println("------loop" + (++count) + " time------ at " + object);

                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.containsKey("z")) {
                        System.out.println(map.get("z"));
                        return false;
                    }
                }
                return true;
            }
        }.transve();
    }
}