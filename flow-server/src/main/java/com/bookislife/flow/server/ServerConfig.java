package com.bookislife.flow.server;

import com.bookislife.flow.core.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class ServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);

    public static final String PROPS_FILE_NAME = "flow.properties";

    public static final String PROPS_SERVER_PORT = "flow.server.port";
    public static final String PROPS_PACKAGE_NAME = "flow.package.name";
    public static final String PROPS_RESOURCE_PATH = "flow.resource.path";

    public static final String DEFAULT_PORT = "10086";

    public final int port;
    public final String packageName;
    public final String resourcePath;

    public ServerConfig(InputStream inputStream) {
        if (inputStream == null) {
            throw new RuntimeException(PROPS_FILE_NAME + " not found.");
        }

        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            port = Integer.valueOf(properties.getProperty(PROPS_SERVER_PORT, DEFAULT_PORT));
            packageName = properties.getProperty(PROPS_PACKAGE_NAME, null);

            Validator.assertNotNull(packageName, PROPS_PACKAGE_NAME + " is missing.");

            resourcePath = properties.getProperty(PROPS_RESOURCE_PATH, packageName + "." + "resource");

        } catch (IOException e) {
            logger.error("Unable load server config.", e);
            throw new RuntimeException(e);
        }
    }

    public ServerConfig() {
        this(ServerConfig.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));
    }

    public int getPort() {
        return port;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServerConfig{");
        sb.append("port='").append(port).append('\'');
        sb.append(", packageName='").append(packageName).append('\'');
        sb.append(", resourcePath='").append(resourcePath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
