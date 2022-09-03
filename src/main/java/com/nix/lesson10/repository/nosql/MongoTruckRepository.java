package com.nix.lesson10.repository.nosql;

import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.repository.CrudRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MongoTruckRepository implements CrudRepository<Truck> {
    private final MongoCollection<Document> trucks;
    private final Gson gson;
    private static final String COLLECTION_NAME = "trucks";

    private static MongoTruckRepository instance;

    private MongoTruckRepository(MongoDatabase db) {
        trucks = db.getCollection(COLLECTION_NAME);
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

    public static synchronized MongoTruckRepository getInstance(MongoDatabase db) {
        if (instance == null) {
            instance = new MongoTruckRepository(db);
        }
        return instance;
    }

    @Override
    public List<Truck> getAll() {
        return trucks.find()
                .map(vehicle -> gson.fromJson(vehicle.toJson(), Truck.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Truck> getById(String id) {
        return Optional.of(findAll(Truck.class, eq("id", id)).get(0));
    }

    @Override
    public Truck create(Truck vehicle) {
        trucks.insertOne(mapToDocument(vehicle));
        return vehicle;
    }

    @Override
    public boolean createList(List<Truck> list) {
        trucks.insertMany(list.stream().map(this::mapToDocument).toList());
        return true;
    }

    @Override
    public boolean update(Truck vehicle) {
        final Document filter = new Document();
        filter.append("id", vehicle.getId());
        Document update = mapToDocument(vehicle);
        Document doc = new Document();
        doc.append("$set", update);
        trucks.updateOne(filter, doc);
        return true;
    }

    @Override
    public Truck delete(String id) {
        Document d = new Document("id", id);
        trucks.deleteOne(d);
        return null;
    }

    private Document mapToDocument(Truck auto) {
        return Document.parse(gson.toJson(auto));
    }

    private List<Truck> findAll(Class<Truck> type, Bson filter) {
        return trucks.find(filter)
                .map(x -> gson.fromJson(x.toJson(), type))
                .into(new ArrayList<>());
    }
}
