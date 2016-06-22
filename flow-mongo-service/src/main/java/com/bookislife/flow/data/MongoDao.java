package com.bookislife.flow.data;

import com.bookislife.flow.core.domain.BaseEntity;
import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.core.utils.ObjectTraverser;
import com.bookislife.flow.core.utils.Validator;
import com.bookislife.flow.data.utils.QueryValidator;
import com.mongodb.DBRef;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by SidneyXu on 2016/05/03.
 */
@Singleton
public class MongoDao implements BaseDao {

    public static final int TIMEOUT = 1000;
    public static final int BATCH_TIMEOUT = 5000;
    public static final int COUNT_TIMEOUT = 10000;
    public static final int DEFAULT_LIMIT = 1000;
    public static final int MAX_LIMIT = 2000;

    private Logger logger = LoggerFactory.getLogger(MongoDao.class);

    private final MongoContext mongoContext;
    private final MongoClientOptions options;

    @Inject
    public MongoDao(MongoContext mongoContext) {
        this.mongoContext = mongoContext;
        this.options = MongoClientOptions.newBuilder()
                .url("127.0.0.1")
                .create();
    }

    private MongoCollection<Document> getCollection(String database, String tableName) {
        return mongoContext.getClient(options)
                .getDatabase(database)
                .getCollection(tableName);
    }

    // TODO: 5/27/16
    //id createdAt updatedAt

    @Override
    public String insert(String database, String tableName, BaseEntity entity) {
        MongoEntity mongoEntity = (MongoEntity) entity;
        Document document = toDocument(mongoEntity);
        getCollection(database, tableName)
                .insertOne(document);
        Object id = document.get("_id");
        if (id instanceof ObjectId) {
            return ((ObjectId) id).toHexString();
        }
        return id.toString();
    }

    @Override
    public int update(String database, String tableName, BaseQuery query, BaseModifier modifier) throws FlowException {
        return (int) getCollection(database, tableName)
                .updateMany(
                        toDocument(query),
                        toDocument(modifier))
                .getModifiedCount();
    }

    private Document toDocument(MongoEntity mongoEntity) {
        Document document = mongoEntity.document;
        return new ObjectTraverser<Document>(document) {
            @Override
            public boolean visit(Object object) {
                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.containsKey("id") && map.get("id") instanceof String) {
                        String id = (String) map.get("id");
                        if (ObjectId.isValid(id)) {
                            map.put("_id", new ObjectId(id));
                        } else {
                            map.put("_id", id);
                        }
                        map.remove("id");
                    } else if (map.containsKey("id") && map.get("id") == null) {
                        map.remove("id");
                    }
                }
                return true;
            }
        }.transve();
    }

    private List<Document> toDocuments(List<? extends BaseEntity> mongoDocuments) {
        return mongoDocuments.stream()
                .map(baseEntity -> toDocument((MongoEntity) baseEntity))
                .collect(Collectors.toList());
    }

    private void iterateMap(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if ("id".equals(entry.getKey()) && entry.getValue() instanceof String) {
                if (ObjectId.isValid((String) entry.getValue())) {
                    map.put("id", new ObjectId((String) entry.getValue()));
                }
            } else if (entry.getValue() instanceof Map) {
                iterateMap((Map<String, Object>) entry.getValue());
            } else if (entry.getValue() instanceof List) {
                iterateList((List<Object>) entry.getValue());
            }
        }
    }

    private void iterateList(List<Object> list) {
        for (Object object : list) {
            if (object instanceof List) {
                iterateList((List<Object>) object);
            } else if (object instanceof Map) {
                iterateMap((Map<String, Object>) object);
            }
        }
    }

    private Document toQuery(Map<String, Object> query) {
        Document document = new Document(query);
        return new ObjectTraverser<Document>(document) {
            @Override
            public boolean visit(Object object) {
                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.containsKey("id") && map.get("id") instanceof Map) {
                        Map tmpMap = (Map) map.get("id");
                        map.remove("id");
                        map.put("_id", tmpMap);
                        Map<String, Object> idCondition = tmpMap;
                        for (Map.Entry<String, Object> entry : idCondition.entrySet()) {
                            if (entry.getValue() instanceof String) {
                                if (ObjectId.isValid((String) entry.getValue())) {
                                    idCondition.put(entry.getKey(), new ObjectId((String) entry.getValue()));
                                }
                            }
                        }
                    }



//                    else if(map.containsKey("$ref")){
//                        // TODO: 16/6/18
//                        String id = (String) map.get("$id");
//                        String ref= (String) map.get("$ref");
//                        new DBRef(ref, new ObjectId(ref));
//                    }
                else if (map.containsKey("$regex") && map.get("$regex") instanceof String) {
                        String regex = (String) map.get("$regex");
                        int flag = 0;
                        String[] array = regex.split("/");
                        if (regex.lastIndexOf('/') != regex.length() - 1) {
                            String strflag = array[2];
                            if (strflag.contains("i")) {
                                flag = Pattern.CASE_INSENSITIVE;
                            }
                            if (strflag.contains("n")) {
                                flag = flag & Pattern.MULTILINE;
                            }
                        }
                        map.put("$regex", Pattern.compile(array[1], flag));
                    }
                }
                return true;
            }
        }.transve();
    }

    private MongoEntity toMongoEntity(Document document) {
        if(null==document)return null;
        Document documentResult = new ObjectTraverser<Document>(document) {
            @Override
            public boolean visit(Object object) {
                if (object instanceof Map) {
                    Map map = (Map) object;
                    if (map.containsKey("_id") && map.get("_id") instanceof ObjectId) {
                        ObjectId id = (ObjectId) map.get("_id");
                        map.remove("_id");
                        map.put("id", id.toHexString());
                    }
                }
                return true;
            }
        }.transve();
        return new MongoEntity(documentResult);
    }

    // TODO: 5/27/16
    private Document toDocument(BaseQuery query) {
        if (null == query) return new Document();
        if (query instanceof MongoQuery) {
            return toQuery(((MongoQuery) query).getQuery());
        }
        throw new IllegalArgumentException("MongoQuery is expected, actual is " + query.getClass().getName());
    }

    private Document toDocument(BaseModifier modifier) {
        if (null == modifier) return new Document();
        return new Document(modifier.getModifiers());
    }

    @Override
    public BaseEntity insertOrUpdate() {
        return null;
    }

    @Override
    public void batchInsert(String database, String tableName, List<? extends BaseEntity> entities) {
        List<Document> documents = toDocuments(entities);
        getCollection(database, tableName)
                .insertMany(documents);
    }

    private Bson idEq(String id) {
        return eq("_id", ObjectId.isValid(id) ? new ObjectId(id) : id);
    }

    @Override
    public int deleteById(String database, String tableName, String id) {
        return (int) getCollection(database, tableName)
                .deleteOne(idEq(id))
                .getDeletedCount();
    }

    @Override
    public int deleteAll(String database, String tableName, BaseQuery query) throws FlowException {
        return (int) getCollection(database, tableName)
                .deleteMany(toDocument(query))
                .getDeletedCount();
    }

    @Override
    public BaseEntity findById(String database, String tableName, String id) {
        Document document = getCollection(database, tableName)
                .find(idEq(id))
                .maxTime(TIMEOUT, TimeUnit.MILLISECONDS)
                .first();
        return toMongoEntity(document);
    }

    @Override
    public BaseEntity findOne(String database, String tableName, BaseQuery query) throws FlowException {
        MongoCollection<Document> collection = getCollection(database, tableName);
        FindIterable<Document> iterable = collection.find(toDocument(query))
                .maxTime(TIMEOUT, TimeUnit.MILLISECONDS);
        Iterator<Document> iterator = iterable.iterator();

        Validator.assertHasNext(iterator, "Object not found.");

        Document document = iterator.next();
        return new MongoEntity(document);
    }

    @Override
    public List<BaseEntity> findAll(String database, String tableName, BaseQuery query) throws FlowException {
        MongoCollection<Document> collection = getCollection(database, tableName);

        int limit = DEFAULT_LIMIT;
        int skip = 0;
        Map<String, Object> sortMap = null;
        if (query != null && query.getConstraint() != null) {
            Constraint constraint = query.getConstraint();
            if (constraint.getLimit() > 0) {
                limit = constraint.getLimit();
            }
            if (constraint.getSkip() > 0) {
                skip = constraint.getSkip();
            }
            if (constraint.getSort() != null) {
                sortMap = new LinkedHashMap<>();
                String[] sorts = constraint.getSort().split(",", -1);
                for (String sort : sorts) {
                    sortMap.put(sort.replaceFirst("[+-]", ""), sort.startsWith("+") ? 1 : -1);
                }
            }
        }

        FindIterable<Document> iterable = collection.find(toDocument(query))
                .limit(limit)
                .skip(skip)
                .maxTime(BATCH_TIMEOUT, TimeUnit.MILLISECONDS);
        if (sortMap != null) {
            iterable.sort(new Document(sortMap));
        }
        if (query != null && query.getProjection() != null) {
            Map<String, Object> projectMap = new HashMap<>();
            query.getProjection()
                    .getSelects()
                    .forEach(s -> projectMap.put(s, 1));
            iterable.projection(new Document(projectMap));
        }
        Iterator<Document> iterator = iterable.iterator();

        Validator.assertHasNext(iterator, "Object not found.");

        List<BaseEntity> entities = new ArrayList<>();

        iterator.forEachRemaining(document ->
                entities.add(toMongoEntity(document)));
        return entities;
    }

    @Override
    public long count(String database, String tableName, BaseQuery query) {
        MongoCollection<Document> collection = getCollection(database, tableName);
        CountOptions options = new CountOptions();
        options.maxTime(COUNT_TIMEOUT, TimeUnit.MILLISECONDS);
        return collection.count(toDocument(query), options);
    }

}
