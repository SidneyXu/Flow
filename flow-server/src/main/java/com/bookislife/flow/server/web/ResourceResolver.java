package com.bookislife.flow.server.web;

import javax.ws.rs.Consumes;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by SidneyXu on 2016/05/18.
 */
public class ResourceResolver {

    public static Resource resolveResource(Class<?> clazz) {
        return new Resource(clazz, resolveClass(clazz), resolveMethod(clazz));
    }

    public static ResourceDescriptor resolveClass(Class<?> clazz) {
        return new ResourceDescriptor(clazz);
    }

    public static List<ResourceDescriptor> resolveMethod(Class<?> clazz) {
        return collectMethods(clazz)
                .map(method -> new ResourceDescriptor(clazz, method))
                .collect(Collectors.toList());
    }

    private static Stream<Method> collectMethods(Class<?> clazz) {
        PrivilegedAction<Method[]> action = clazz::getMethods;
        Method[] methods = AccessController.doPrivileged(action);
        return Stream.of(methods)
                .filter(ResourceResolver::checkMethod);
    }

    private static boolean checkMethod(Method method) {
        if (method.getAnnotation(Path.class) != null
                || method.getAnnotation(Consumes.class) != null
                || method.getAnnotation(Produces.class) != null)
            return true;

        return Stream.of(method.getAnnotations())
                .filter(annotation ->
                        annotation
                                .annotationType()
                                .getAnnotation(HttpMethod.class) != null)
                .findFirst()
                .isPresent();
    }
}
