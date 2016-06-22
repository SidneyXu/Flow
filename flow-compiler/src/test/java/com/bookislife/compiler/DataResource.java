package com.bookislife.compiler;

import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by SidneyXu on 2016/06/22.
 */
@Path("/classes")
public class DataResource {

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(RoutingContext context) {
        HttpServerRequest request = context.request();
        return "{\"x\":1}";
    }

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    private void internalCreate(RoutingContext context) {
        HttpServerRequest request = context.request();
        context.response().putHeader("Content-Type", "application/json")
                .end("foobar");
    }
}
