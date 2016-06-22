package com.bookislife.compiler

import com.bookislife.flow.compiler.ResourceProcessor
import com.google.common.truth.Truth.*
import com.google.testing.compile.JavaFileObjects
import java.net.URL
import javax.tools.JavaFileObject
import com.google.testing.compile.JavaSourceSubjectFactory.javaSource
import org.junit.Test

/**
 * Created by SidneyXu on 2016/06/22.
 */
class DataResourceTest{

    @Test
    fun test01(){
        val sourceFile = JavaFileObjects.forSourceString("DataResource","""
        package com.bookislife.compiler;

import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/classes")
public class DataResource {

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(RoutingContext context) {
        HttpServerRequest request = context.request();
        return "{\"x\":1}";
    }

    @GET
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public String get(RoutingContext context, String id) {
        HttpServerRequest request = context.request();
        return "{\"x\":1}";
    }
}
        """)

        val expected = JavaFileObjects.forSourceString("DataSourceHandler","""
        package com.bookislife.compiler;

import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/classes")
public class DataSourceHandler {

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(RoutingContext context) {
        HttpServerRequest request = context.request();
        return "{\"x\":1}";
    }
}
        """)

        assertAbout(javaSource())
        .that(sourceFile)
        .processedWith(ResourceProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expected)
    }
}