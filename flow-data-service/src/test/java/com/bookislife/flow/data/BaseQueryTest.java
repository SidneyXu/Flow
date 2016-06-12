package com.bookislife.flow.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by SidneyXu on 2016/05/13.
 */
public class BaseQueryTest {

    private ObjectWriter writer;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        writer = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void testCore() throws Exception {
        Condition condition = new Condition.Builder()
                .addCondition("$eq", "x", 1)
                .addCondition("$in", "color", Arrays.asList("yellow", "blue"))
                .addCondition("$exists", "name", true)
                .create();


        writer.writeValue(System.out, condition);
        /*
            {
                x: {
                    "$eq":1
                },
                color: {
                    "$in":[yellow, blue]
                },
                name: {
                    exists: true
                }
            }
         */
    }

    @Test
    public void testLike() throws Exception {
        Condition condition = new Condition.Builder()
                .addCondition("$like", "name", "/Jack.*/i")
                .addCondition("$exists", "name", true)
                .create();
        writer.writeValue(System.out, condition);
    }

    @Test
    public void testLink() throws Exception {
        BaseQuery blogQuery = BaseQuery.from("t_blog");
        BaseQuery commentQuery = BaseQuery.from("t_comment");

        Condition condition = new Condition.Builder()
                .addCondition("$link", "id", new Condition.Link("t_blog", "id"))
                .create();
        writer.writeValue(System.out, condition);

        /*
            {
                id: {
                  "$link":"t_blog.id"
                }
            }
         */
    }

    @Test
    public void testQuery() throws Exception {
        BaseQuery commentQuery = BaseQuery.from("t_comment");
        Condition condition = commentQuery.newCondition()
                .eq("author", "Jane")
                .gt("publish_date", new Date())
                .link("blog_id", new Condition.Link("t_blog", "id"))
                .create();
        commentQuery.setCondition(condition);

        Constraint constraint = commentQuery.newConstraint()
                .limit(100)
                .skip(10)
                .include("title")
                .include("description")
                .sort("id", true)
                .sort("name", false)
                .create();
        commentQuery.setConstraint(constraint);

        Projection projection = commentQuery.newProjection()
                .select("id")
                .select("name")
                .create();
        commentQuery.setProjection(projection);


        writer.writeValue(System.out, commentQuery);
    }

    @Test
    public void testSelect() throws Exception {
        BaseQuery commentQuery = BaseQuery.from("t_comment");
        Condition condition = commentQuery.newCondition()
                .eq("author", "Jane")
                .gt("publish_date", new Date())
                .link("blog_id", new Condition.Link("t_blog", "id"))
                .create();
        commentQuery.setCondition(condition);

        Constraint constraint = commentQuery.newConstraint()
                .limit(100)
                .skip(10)
                .include("title")
                .include("description")
                .sort("id", true)
                .sort("name", false)
                .create();
        commentQuery.setConstraint(constraint);

        Projection projection = commentQuery.newProjection()
                .select("id")
                .select("name")
                .create();
        commentQuery.setProjection(projection);


        writer.writeValue(System.out, commentQuery);
    }

    @Test
    public void testModifier() throws Exception {
        BaseModifier modifier= BaseModifier.newBuilder()
                .addUpdater(BaseModifier.INC,"name",100)
                .create();


        writer.writeValue(System.out, modifier);
    }
}