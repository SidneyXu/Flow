package com.bookislife.flow.server.resource;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.DBStorage;
import com.bookislife.flow.server.utils.ResponseCreator;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by SidneyXu on 2016/05/12.
 */
@Singleton
@Path("/classes")
public class DataResource {

    private DBStorage storage;

    @Inject
    public DataResource(DBStorage storage) {
        this.storage = storage;
    }

    @POST
    @Path(":className")
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(RoutingContext context) throws FlowException {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String bodyString = context.getBodyAsString();
        BaseEntity entity = storage.insert(databaseName, tableName, bodyString);
        return ResponseCreator.newCreateResponse(entity);
    }

    @GET
    @Path(":className/:objectId")
    public String get(RoutingContext context) throws FlowException {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        BaseEntity entity = storage.findById(databaseName, tableName, objectId);
        return ResponseCreator.newQueryResponse(entity);
    }

    @DELETE
    @Path(":className/:objectId")
    public String delete(RoutingContext context) throws FlowException {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        int n = storage.delete(databaseName, tableName, objectId);
        return ResponseCreator.newDeleteResponse(n);
    }

//    @POST
//    @Path(":className/batch")
//    public void batch(RoutingContext context) {
//
//    }

    @PUT
    @Path(":className/:objectId")
    public void update(RoutingContext context) {
        HttpServerRequest request = context.request();
        String tableName = request.getParam("className");
        String objectId = request.getParam("objectId");

        // TODO: 5/25/16
    }

    //TODO
    @POST
    @Path("count")
    public String count(RoutingContext context) throws FlowException {
        HttpServerRequest request = context.request();
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String tableName = request.getParam("className");
        String query = context.getBodyAsString();
        long n = storage.count(databaseName, tableName, query);
        return ResponseCreator.newCountResponse(n);
    }

    @POST
    @Path(":className/query")
    @Consumes(MediaType.APPLICATION_JSON)
    public void findAll(RoutingContext context) {
        HttpServerRequest request = context.request();
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String tableName = request.getParam("className");
        String query = context.getBodyAsString();
        try {
            List<BaseEntity> entities = storage.findAll(databaseName, tableName, query);
        } catch (FlowException e) {
            e.printStackTrace();
        }

        // TODO: 5/25/16
    }

}
