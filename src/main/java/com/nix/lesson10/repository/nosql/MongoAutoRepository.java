package com.nix.lesson10.repository.nosql;

import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.repository.CrudRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoAutoRepository implements CrudRepository<Auto> {
    private final MongoCollection<Document> autos;
    private final Gson gson;
    private static final String COLLECTION_NAME = "autos";

    private static MongoAutoRepository instance;

    private MongoAutoRepository(MongoDatabase db) {
        autos = db.getCollection(COLLECTION_NAME);
        JsonSerializer<LocalDateTime> ser = (localDateTime, type, jsonSerializationContext) ->
                localDateTime == null ? null : new JsonPrimitive(localDateTime.toString());
        JsonDeserializer<LocalDateTime> deser = (json, typeOfT, context) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
//            return LocalDateTime.parse(json.getAsString(), formatter);
            return LocalDateTime.now();
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, ser)
                .registerTypeAdapter(LocalDateTime.class, deser).create();
    }

    public static synchronized MongoAutoRepository getInstance(MongoDatabase db) {
        if (instance == null) {
            instance = new MongoAutoRepository(db);
        }
        return instance;
    }

    @Override
    public List<Auto> getAll() {
        return autos.find()
                .map(vehicle -> gson.fromJson(vehicle.toJson(), Auto.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Auto> getById(String id) {
        return Optional.of(findAll(Auto.class, eq("id", id)).get(0));
    }

    @Override
    public Auto create(Auto vehicle) {
        autos.insertOne(mapToDocument(vehicle));
        return vehicle;
    }

    @Override
    public boolean createList(List<Auto> list) {
        autos.insertMany(list.stream().map(this::mapToDocument).toList());
        return true;
    }

    @Override
    public boolean update(Auto vehicle) {
        final Document filter = new Document();
        filter.append("id", vehicle.getId());
        Document update = mapToDocument(vehicle);
        Document doc = new Document();
        doc.append("$set", update);
        autos.updateOne(filter, doc);
        return false;
    }

    @Override
    public Auto delete(String id) {
        Document d = new Document("id", id);
        autos.deleteOne(d);
        return null;
    }

    private Document mapToDocument(Auto auto) {
        return Document.parse(gson.toJson(auto));
    }

    private List<Auto> findAll(Class<Auto> type, Bson filter) {
        return autos.find(filter)
                .map(x -> gson.fromJson(x.toJson(), type))
                .into(new ArrayList<>());
    }
}
