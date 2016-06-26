package com.bookislife.flow.sdk;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.domain.BaseResponse;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.data.BaseModifier;
import com.bookislife.flow.data.BaseQuery;
import com.bookislife.flow.sdk.parser.JSONParser;
import com.bookislife.flow.sdk.web.FBody;
import com.bookislife.flow.sdk.web.FRequest;
import com.bookislife.flow.sdk.web.FResponse;
import com.bookislife.flow.sdk.web.RequestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public class ObjectServiceImpl implements ObjectService {

    private ConsoleLogService logService;
    private RequestClient requestClient;
    private String applicationId;
    private String targetUrl;

    public ObjectServiceImpl(FlowApi api, RequestClient requestClient) {
        this.requestClient = requestClient;
        this.applicationId = api.applicationId;
        this.targetUrl = api.targetUrl;
    }

    @Override
    public void save(String type, BaseEntity entity) throws FlowException {
        String url = String.format(
                "%s/classes/%s",
                targetUrl,
                type
        );
        FBody body = FBody.fromJson(JSONParser.encode(entity.getData()));
        FRequest request = FRequest.newBuilder()
                .addHeader(Env.Header.APPLICATION_ID, applicationId)
                .post(url)
                .body(body)
                .build();
        FResponse response = requestClient.execute(request);
        if (response.isJsonResponse()) {
            BaseResponse result = JSONParser.decode(response.getBodyAsString(), BaseResponse.class);
            if (response.isSuccessful()) {
                long createdAt = result.getCreatedAt();
                String id = result.getId();
                entity.setId(id);
                entity.setCreatedAt(createdAt);
                entity.setUpdatedAt(createdAt);
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }

    @Override
    public int delete(String type, String id) throws FlowException {
        String url = String.format(
                "%s/classes/%s/%s",
                targetUrl,
                type,
                id
        );
        FRequest request = FRequest.newBuilder()
                .addHeader(Env.Header.APPLICATION_ID, applicationId)
                .delete(url)
                .build();
        FResponse response = requestClient.execute(request);

        if (response.isJsonResponse()) {
            BaseResponse result = JSONParser.decode(response.getBodyAsString(), BaseResponse.class);
            if (response.isSuccessful()) {
                return result.getCount();
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }

    @Override
    public BaseEntity get(String type, String id) throws FlowException {
        return get(BaseEntity.class, type, id);
    }

    @Override
    public <T extends BaseEntity> T get(Class<T> clazz, String type, String id) throws FlowException {
        String url = String.format(
                "%s/classes/%s/%s",
                targetUrl,
                type,
                id
        );
        FRequest request = FRequest.newBuilder()
                .addHeader(Env.Header.APPLICATION_ID, applicationId)
                .get(url)
                .build();
        FResponse response = requestClient.execute(request);
        if (response.isJsonResponse()) {
            BaseResponse result = JSONParser.decode(response.getBodyAsString(), BaseResponse.class);
            if (response.isSuccessful()) {
                T entity;
                try {
                    entity = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                entity.setData(result.getData());
                return entity;
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }

    @Override
    public List<? extends BaseEntity> find(BaseQuery query) throws FlowException {
        String url = String.format(
                "%s/classes/%s/query",
                targetUrl,
                query.getTableName()
        );
        FBody body = FBody.fromJson(JSONParser.encode(query));
        FRequest request = FRequest.newBuilder()
                .addHeader(Env.Header.APPLICATION_ID, applicationId)
                .post(url)
                .body(body)
                .build();
        FResponse response = requestClient.execute(request);
        if (response.isJsonResponse()) {
            BaseResponse result = JSONParser.decode(response.getBodyAsString(), BaseResponse.class);
            if (response.isSuccessful()) {
                List<Map<String, Object>> results = result.getDatas();
                return results.stream().map(map -> {
                    BaseEntity entity = new BaseEntity();
                    entity.setData(map);
                    return entity;
                }).collect(Collectors.toList());
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }

    @Override
    public int update(String type, String id, BaseModifier modifier) throws FlowException {
        String url = String.format(
                "%s/classes/%s/%s",
                targetUrl,
                type,
                id
        );
        FBody body = FBody.fromJson(JSONParser.encode(modifier));
        FRequest request = FRequest.newBuilder()
                .addHeader(Env.Header.APPLICATION_ID, applicationId)
                .put(url)
                .body(body)
                .build();

        System.out.printf(body.toString());

        FResponse response = requestClient.execute(request);

        System.out.println(response.getBodyAsString());

        if (response.isJsonResponse()) {
            BaseResponse result = JSONParser.decode(response.getBodyAsString(), BaseResponse.class);
            if (response.isSuccessful()) {
                int count = result.getCount();
                return count;
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }


}
