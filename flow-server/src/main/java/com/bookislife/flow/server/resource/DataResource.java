package com.bookislife.flow.server.resource;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.core.utils.Pair;
import com.bookislife.flow.data.DBStorage;
import com.bookislife.flow.server.domain.RoutingContextWrapper;
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
    public String create(RoutingContextWrapper context) throws FlowException {
        BaseEntity entity = storage.insert(context.getDatabaseName(), context.getTableName(), context.getBodyAsString());
        return ResponseCreator.newCreateResponse(entity);
    }

    @GET
    @Path(":className/:objectId")
    public String get(RoutingContextWrapper context) throws FlowException {
        BaseEntity entity = storage.findById(context.getDatabaseName(), context.getTableName(), context.getObjectId());
        return ResponseCreator.newQueryResponse(entity);
    }

    @DELETE
    @Path(":className/:objectId")
    public String delete(RoutingContextWrapper context) throws FlowException {
        int n = storage.delete(context.getDatabaseName(), context.getTableName(), context.getObjectId());
        return ResponseCreator.newDeleteResponse(n);
    }

//    @POST
//    @Path(":className/batch")
//    public void batch(RoutingContext context) {
//
//    }

    @PUT
    @Path(":className/:objectId")
    public String update(RoutingContextWrapper context) throws FlowException {
        Pair<Integer, Long> pair= storage.updateById(context.getDatabaseName(), context.getTableName(), context.getObjectId(), context.getBodyAsString());
        return ResponseCreator.newUpdateResponse(pair);
    }

    @POST
    @Path(":className/count")
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
    public String findAll(RoutingContext context) throws FlowException {
        HttpServerRequest request = context.request();
        String databaseName = request.getHeader(Env.Header.APPLICATION_ID);
        String tableName = request.getParam("className");
        String query = context.getBodyAsString();
        List<BaseEntity> entities = storage.findAll(databaseName, tableName, query);
        return ResponseCreator.newQueryResponse(entities);
    }

}
