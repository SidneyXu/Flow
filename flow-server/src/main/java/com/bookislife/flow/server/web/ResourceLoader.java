package com.bookislife.flow.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by SidneyXu on 2016/05/16.
 */
public class ResourceLoader {

    public static final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    private ClassLoader classLoader;

    public ResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Set<Class<?>> scanPackage(String packageName) {
        String path = packageName.replace('.', File.separatorChar);
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(path);
            Set<Class<?>> classSet = new HashSet<>();
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                Set<String> classPath = scanURL(packageName, url);
                classSet.addAll(loadClass(classPath));
            }
            return classSet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Set<String> scanURL(String packageName, URL url) {
        if ("jar".equals(url.getProtocol())) {
            return visitJars(packageName, url);
        } else {
            return visitFiles(packageName, url);
        }
    }

    private Set<String> visitJars(String packageName, URL url) {
        String externalFrom = url.toExternalForm();
        String jarPath = externalFrom.substring(externalFrom.indexOf("file:") + 5);
        jarPath = jarPath.substring(0, jarPath.indexOf('!'));
        try {
            JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));

            String path = packageName.replace('.', File.separatorChar);
            Enumeration<JarEntry> entries = jarFile.entries();
            Set<String> stringSet = new HashSet<>();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.isDirectory()
                        && entry.getName().endsWith(".class")
                        && entry.getName().contains(path)) {
                    stringSet.add(entry.getName());
                }
            }
            return stringSet.stream()
                    .map(classPath -> classPath.replace(".class", "")
                            .substring(classPath.indexOf(path))
                            .replace(File.separatorChar, '.'))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    private Set<String> visitFiles(String packageName, URL url) {
        try {
            String path = packageName.replace('.', File.separatorChar);
            List<String> paths = new ArrayList<>();
            Files.walkFileTree(Paths.get(url.getPath()), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // be careful, toString() is necessary
                    if (file.toString().endsWith(".class")) {
                        paths.add(file.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            return paths
                    .stream()
                    .map(classPath ->
                            classPath.replace(".class", "")
                                    .substring(classPath.indexOf(path))
                                    .replace(File.separatorChar, '.')
                    ).collect(Collectors.toSet());
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    public Set<Class<?>> loadClass(Set<String> classPath) {
        return classPath.stream()
                .map((Function<String, Class<?>>) s -> {
                    try {
                        return Class.forName(s);
                    } catch (ClassNotFoundException e) {
                        logger.error("Unable to load " + s);
                    }
                    return null;
                }).filter(aClass -> aClass != null)
                .collect(Collectors.toSet());
    }

}
