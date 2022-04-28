package repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import configuration.MongoDbConfig;
import dtos.Fruit;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.bson.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MongoDbFruitRepository implements FruitRepository {

    private final MongoDbConfig mongoConf;
    private final MongoClient mongoClient;

    public MongoDbFruitRepository(MongoDbConfig mongoConf,
                                  MongoClient mongoClient) {
        this.mongoConf = mongoConf;
        this.mongoClient = mongoClient;
    }

    @Override
    public void save(@NonNull @NotNull @Valid Fruit fruit) {
        getCollection().insertOne(fruit);
        new Document("name", "Apple");
    }

    @Override
    @NonNull
    public List<Fruit> list() {
        return getCollection().find().into(new ArrayList<>());
    }

    @NonNull
    private MongoCollection<Fruit> getCollection() {
        return mongoClient.getDatabase(mongoConf.getName())
                .getCollection(mongoConf.getCollection(), Fruit.class);
    }
}