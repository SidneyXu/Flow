package com.bookislife.flow.data.module;

import com.bookislife.flow.data.DriverManager;
import com.google.inject.Binder;
import com.google.inject.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.bookislife.flow.core.Env.Config;
import static com.bookislife.flow.core.Env.File;

/**
 * Created by SidneyXu on 2016/05/23.
 */
public class DataServiceModule implements Module {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceModule.class);

    public static final String DIALECT_MONGODB = "mongodb";

    @Override
    public void configure(Binder binder) {
        DriverManager.setBinder(binder);

        try (InputStream inputStream = DataServiceModule.class.getClassLoader().getResourceAsStream(File.FLOW_DB_CONFIG_FILE_NAME);) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String dialect = properties.getProperty(Config.DB_DIALECT, DIALECT_MONGODB);

            if (DIALECT_MONGODB.endsWith(dialect)) {
                Class.forName("com.bookislife.flow.data.MongoDriver");
            } else {
                throw new RuntimeException(String.format("dialect %s is invalid.", dialect));
            }

        } catch (IOException e) {
            logger.error("Unable load server config.", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.error("Unable load class.", e);
            throw new RuntimeException(e);
        }
    }
}
