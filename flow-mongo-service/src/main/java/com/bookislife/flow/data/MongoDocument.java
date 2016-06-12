package com.bookislife.flow.data;

import com.bookislife.flow.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.bson.Document;

import java.util.Collections;
import java.util.Map;

/**
 * Created by SidneyXu on 2016/05/04.
 */
public class MongoDocument extends BaseEntity {

    private Map<String, Object> data;
    public Document document;

    public MongoDocument(Document document) {
        this.document = document;
        this.data = document;
    }

    @JsonCreator
    public MongoDocument(Map<String, Object> map) {
        this.document = new Document(map);
        parseData(map);
    }

    @SuppressWarnings("unchecked")
    private void parseData(Map<String, Object> map) {
        data = (Map<String, Object>) map.getOrDefault(FIELD_DATA, null);
        setCreatedAt((Long) map.getOrDefault(FIELD_CREATED_AT, 0L));
        setUpdatedAt((Long) map.getOrDefault(FIELD_UPDATED_AT, getCreatedAt()));
        setId((String) map.getOrDefault(FIELD_ID, null));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoDocument{");
        sb.append("document=").append(document);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
        document.put(FIELD_ID, id);
    }

    @Override
    public void setCreatedAt(long createdAt) {
        super.setCreatedAt(createdAt);
        document.put(FIELD_CREATED_AT, createdAt);
    }

    @Override
    public void setUpdatedAt(long updatedAt) {
        super.setUpdatedAt(updatedAt);
        document.put(FIELD_UPDATED_AT, updatedAt);
    }

    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }

}