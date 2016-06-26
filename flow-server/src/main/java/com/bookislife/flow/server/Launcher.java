package com.bookislife.flow.server;

import com.bookislife.flow.core.exception.FlowException;
import com.bookislife.flow.server.domain.RoutingContextWrapper;
import com.bookislife.flow.server.utils.ResponseCreator;
import com.bookislife.flow.server.utils.Runner;
import com.bookislife.flow.server.web.ResourceDescriptor;
import com.bookislife.flow.server.web.ResourceLoader;
import com.bookislife.flow.server.web.ResourceResolver;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.RoutingContext;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Route;
import io.vertx.rxjava.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by SidneyXu on 2016/06/06.
 */
public class Launcher extends AbstractVerticle {

    public static final Logger logger = LoggerFactory.getLogger(Launcher.class);

    private Injector injector;
    private ServerConfig serverConfig;

    private final ExecutorService executorService = Executors.newWorkStealingPool();

    public static void main(String[] args) {
        Runner.runExample(Launcher.class);
    }

    @Override
    public void start() throws Exception {
        initConfig();

        // init web server
        Router router = Router.router(vertx);
        registerGlobalHandler(router);
        registerResourceHandler(router);

        // TODO: 5/25/16 performance
        HttpServerOptions options = new HttpServerOptions();
        vertx.createHttpServer(options)
                .requestHandler(router::accept)
                .listen(serverConfig.port);
    }

    private void registerGlobalHandler(Router router) {
        Middleware middleware = injector.getInstance(Middleware.class);

        router.route().failureHandler(middleware.getExceptionHandler());
        router.route().handler(middleware.getResponseTimeHandler());
        router.route().handler(middleware.getCookieHandler());
        router.route().handler(middleware.getBodyHandler());
        // TODO: 16/6/2
//        router.route().handler(middleware.getStaticResourceHandler());
        router.route().handler(middleware.getRedirectHandler());
        router.route().handler(middleware.getCrossDomainHandler());
    }

    private void applyRoute(Route route, ResourceDescriptor cd, ResourceDescriptor md) {
        if (null == md.path) {
            route.path(cd.path);
        } else {
            route.path("/".equals(cd.path) ? md.path + md.path : cd.path + "/" + md.path);
        }
        if (null != md.httpMethod) {
            route.method(HttpMethod.valueOf(md.httpMethod));
        }
        if (null != md.consumeType) {
            md.consumeType.forEach(route::consumes);
        } else if (null != cd.consumeType) {
            cd.consumeType.forEach(route::consumes);
        }
        if (null != md.produceType) {
            md.produceType.forEach(route::produces);
        } else if (null != cd.produceType) {
            cd.produceType.forEach(route::produces);
        }
    }

    private void registerResourceHandler(Router rootRouter) {
        ResourceLoader resourceLoader = new ResourceLoader(Launcher.class.getClassLoader());
        Set<Class<?>> classSet = resourceLoader.scanPackage(serverConfig.resourcePath);

        classSet.stream()
                .map(ResourceResolver::resolveResource)
                .forEach(resource -> {
                    ResourceDescriptor clazzDescriptor = resource.getClassDescriptor();
                    resource.getMethodDescriptorList().forEach(methodDescriptor -> {
                        Object singleton = injector.getInstance(resource.clazz);

                        Route route = rootRouter.route();
                        applyRoute(route, clazzDescriptor, methodDescriptor);
                        route.handler(ctx -> CompletableFuture.runAsync(() -> {

                            Method method = methodDescriptor.method;
                            ImmutableList<String> consumeTypes = methodDescriptor.consumeType;
                            String defaultConsumeType = MediaType.APPLICATION_JSON;
                            if (consumeTypes != null && consumeTypes.size() > 0) {
                                defaultConsumeType = consumeTypes.get(0);
                            }

                            // TODO: 5/19/16 add interceptor

                            try {
                                assert method != null;
                                RoutingContextWrapper wrapper = new RoutingContextWrapper((RoutingContext) ctx.getDelegate());
                                Object result = method.invoke(singleton, wrapper);
                                if (result != null) {
                                    if (defaultConsumeType.contains(MediaType.APPLICATION_JSON)) {
                                        ctx.response()
                                                .putHeader("Content-Type", MediaType.APPLICATION_JSON)
                                                .end(result.toString());
                                    }
                                }
                            } catch (IllegalAccessException e) {
                                logger.error("error occurs when invoking methods", e);
                                e.printStackTrace();
                            }
                            // process errors from resource handler
                            catch (InvocationTargetException e) {
                                Throwable cause = e.getCause();
                                if (e.getCause() instanceof FlowException) {
                                    ctx.response().setStatusCode(400).putHeader("Content-Type", MediaType.APPLICATION_JSON).end(ResponseCreator.newErrorResponse((FlowException) cause));
                                    return;
                                }
                                logger.error("error occurs when invoking methods", e);
                                ctx.response().setStatusCode(400).putHeader("Content-Type", MediaType.APPLICATION_JSON).end(ResponseCreator.newErrorResponse(cause.getMessage()));
                            }
                        }, executorService));
                    });
                });
    }


    private void initConfig() {
        // ioc
        injector = Guice.createInjector(new ServerModule());

        serverConfig = new ServerConfig();
    }

    public Injector getInjector() {
        return injector;
    }
}