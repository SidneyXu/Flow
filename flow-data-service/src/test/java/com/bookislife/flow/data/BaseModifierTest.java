package com.bookislife.flow.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SidneyXu on 2016/06/12.
 */
public class BaseModifierTest {

    private ObjectWriter writer;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        writer = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void testModifier() throws Exception {
        BaseModifier modifier= BaseModifier.newBuilder()
                .addUpdater(BaseModifier.INC,"name",100)
                .create();


        writer.writeValue(System.out, modifier);
    }
}