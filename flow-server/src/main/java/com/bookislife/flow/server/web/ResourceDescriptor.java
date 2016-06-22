package com.bookislife.flow.server.web;


import com.google.common.collect.ImmutableList;

import javax.ws.rs.*;
import java.lang.reflect.Method;

/**
 * Created by SidneyXu on 2016/05/17.
 */
public class ResourceDescriptor {

    public final Class<?> clazz;
    public final Method method;
    public final String httpMethod;
    public final String path;
    public final ImmutableList<String> consumeType;
    public final ImmutableList<String> produceType;

    public ResourceDescriptor(Class<?> clazz) {
        this.clazz = clazz;
        this.method = null;
        this.httpMethod = null;
        if (clazz.isAnnotationPresent(Path.class)) {
            path = clazz.getAnnotation(Path.class).value();
        } else {
            path = null;
        }
        if (clazz.isAnnotationPresent(Consumes.class)) {
            String[] consumes = clazz.getAnnotation(Consumes.class).value();
            consumeType = ImmutableList.copyOf(consumes);
        } else {
            consumeType = null;
        }
        if (clazz.isAnnotationPresent(Produces.class)) {
            String[] produces = clazz.getAnnotation(Produces.class).value();
            produceType = ImmutableList.copyOf(produces);
        } else {
            produceType = null;
        }
    }

    public ResourceDescriptor(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
        if (method.isAnnotationPresent(Path.class)) {
            path = method.getAnnotation(Path.class).value();
        } else {
            path = null;
        }
        if (method.isAnnotationPresent(Consumes.class)) {
            String[] consumes = method.getAnnotation(Consumes.class).value();
            consumeType = ImmutableList.copyOf(consumes);
        } else {
            consumeType = null;
        }
        if (method.isAnnotationPresent(Produces.class)) {
            String[] produces = method.getAnnotation(Produces.class).value();
            produceType = ImmutableList.copyOf(produces);
        } else {
            produceType = null;
        }
        if (method.isAnnotationPresent(HttpMethod.class)) {
            httpMethod = method.getAnnotation(HttpMethod.class).value();
        } else if (method.isAnnotationPresent(GET.class)) {
            httpMethod = HttpMethod.GET;
        } else if (method.isAnnotationPresent(POST.class)) {
            httpMethod = HttpMethod.POST;
        } else if (method.isAnnotationPresent(PUT.class)) {
            httpMethod = HttpMethod.PUT;
        } else if (method.isAnnotationPresent(DELETE.class)) {
            httpMethod = HttpMethod.DELETE;
        } else {
            httpMethod = null;
        }

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResourceDescriptor{");
        sb.append("clazz=").append(clazz);
        sb.append(", method=").append(method);
        sb.append(", httpMethod='").append(httpMethod).append('\'');
        sb.append(", path='").append(path).append('\'');
        sb.append(", consumeType=").append(consumeType);
        sb.append(", produceType=").append(produceType);
        sb.append('}');
        return sb.toString();
    }
}
