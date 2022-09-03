package com.nix.lesson10.repository.nosql;

import com.google.gson.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nix.lesson10.model.Invoice;
import com.nix.lesson10.repository.InvoiceRepository;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoInvoiceRepository implements InvoiceRepository {
    private final MongoCollection<Document> invoices;
    private final Gson gson;
    private static final String COLLECTION_NAME = "invoices";

    private static MongoInvoiceRepository instance;

    private MongoInvoiceRepository(MongoDatabase db) {
        invoices = db.getCollection(COLLECTION_NAME);
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

    public static synchronized MongoInvoiceRepository getInstance(MongoDatabase db) {
        if (instance == null) {
            instance = new MongoInvoiceRepository(db);
        }
        return instance;
    }

    @Override
    public List<Invoice> getAll() {
        return invoices.find()
                .map(invoice -> gson.fromJson(invoice.toJson(),Invoice.class))
                .into(new ArrayList<>());
    }

    @Override
    public Optional<Invoice> getById(String id) {
        return Optional.empty();
    }

    @Override
    public Invoice create(Invoice invoice) {
        invoices.insertOne(parseToDocument(invoice));
        return invoice;
    }

    @Override
    public boolean createList(List<Invoice> list) {
        invoices.insertMany(list.stream().map(this::parseToDocument).toList());
        return true;
    }

    @Override
    public boolean update(Invoice invoice) {
        final Document filter = new Document();
        filter.append("id", invoice.getId());
        Document update = parseToDocument(invoice);
        Document doc = new Document();
        doc.append("$set", update);
        invoices.updateOne(filter, doc);
        return true;
    }

    @Override
    public Invoice delete(String id) {
        Document d = new Document("id",id);
        invoices.deleteOne(d);
        return null;
    }

    private Document parseToDocument(Invoice invoice){
        return Document.parse(gson.toJson(invoice));
    }
}
