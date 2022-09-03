package com.nix.lesson10.repository.nosql;

import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.CrudRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoMotoRepository implements CrudRepository<Motorcycle> {
    private final MongoCollection<Document> motos;
    private final Gson gson;
    private static final String COLLECTION_NAME = "motos";

    private static MongoMotoRepository instance;

    private MongoMotoRepository(MongoDatabase db) {
        motos = db.getCollection(COLLECTION_NAME);
        JsonSerializer<LocalDateTime> ser = (localDateTime, type, jsonSerializationContext) ->
                localDateTime == null ? null : new JsonPrimitive(localDateTime.toString());
        JsonDeserializer<LocalDateTime> deser = (json, typeOfT, context) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
            return LocalDateTime.now();
        };
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, ser)
                .registerTypeAdapter(LocalDateTime.class, deser).create();
    }

    public static synchronized MongoMotoRepository getInstance(MongoDatabase db) {
        if (instance == null) {
            instance = new MongoMotoRepository(db);
        }
        return instance;
    }

    @Override
    public List<Motorcycle> getAll() {
        return motos.find()
                .map(vehicle -> gson.fromJson(vehicle.toJson(), Motorcycle.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Motorcycle> getById(String id) {
        return Optional.of(findAll(Motorcycle.class, eq("id", id)).get(0));
    }

    @Override
    public Motorcycle create(Motorcycle vehicle) {
        motos.insertOne(mapToDocument(vehicle));
        return vehicle;
    }

    @Override
    public boolean createList(List<Motorcycle> list) {
        motos.insertMany(list.stream().map(this::mapToDocument).toList());
        return true;
    }

    @Override
    public boolean update(Motorcycle vehicle) {
        final Document filter = new Document();
        filter.append("id", vehicle.getId());
        Document update = mapToDocument(vehicle);
        Document doc = new Document();
        doc.append("$set", update);
        motos.updateOne(filter, doc);
        return false;
    }

    @Override
    public Motorcycle delete(String id) {
        Document d = new Document("id",id);
        motos.deleteOne(d);
        return null;
    }

    private Document mapToDocument(Motorcycle moto) {
        return Document.parse(gson.toJson(moto));
    }

    private List<Motorcycle> findAll(Class<Motorcycle> type, Bson filter) {
        return motos.find(filter)
                .map(x -> gson.fromJson(x.toJson(), type))
                .into(new ArrayList<>());
    }
}
