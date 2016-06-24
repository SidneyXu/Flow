package com.bookislife.flow.sdk;

import com.bookislife.flow.core.Env;
import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.domain.BaseResponse;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.sdk.parser.JSONParser;
import com.bookislife.flow.sdk.web.FBody;
import com.bookislife.flow.sdk.web.FRequest;
import com.bookislife.flow.sdk.web.FResponse;
import com.bookislife.flow.sdk.web.RequestClient;

/**
 * Created by SidneyXu on 2016/06/16.
 */
public class ObjectServiceImpl implements ObjectService {

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
        FBody body = FBody.fromJson(JSONParser.encode(entity));
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
    public void delete(String id) {

    }

    @Override
    public BaseEntity get(String type, String id) throws FlowException {
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
                System.out.println(result);

                // TODO: 6/24/16

                BaseEntity entity = new BaseEntity();
                entity.setData(result.getData());
                return entity;
            } else {
                throw new FlowException(result.getErrorCode(), result.getErrorMessage());
            }
        } else {
            throw new FlowException(response.getStatusCode(), response.getBodyAsString());
        }
    }
}
