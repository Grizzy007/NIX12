package com.nix.lesson10.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String USERNAME = "Grizzy";
    private static final String PASSWORD = "paS13J894GtEMuyP";
    private static final String URI = "mongodb+srv://" + USERNAME +
            ":" + PASSWORD + "@vehicles.p9nkvug.mongodb.net/?retryWrites=true&w=majority";
    private static MongoClient mongoClient;

    private MongoUtil() {}

    public static MongoDatabase getConnection(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }

    public static MongoDatabase connect(String databaseName) {
        return getMongoClient().getDatabase(databaseName);
    }

    private static MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
        }
        return mongoClient;
    }
}
