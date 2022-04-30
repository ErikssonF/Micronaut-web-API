package repository;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import configuration.MongoDbConfig;
import dtos.Fruit;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    public Mono<Boolean> save(@NonNull @NotNull @Valid Fruit fruit) {
        return Mono.from(getCollection().insertOne(fruit))
                .map(insertOneResult -> true)
                .onErrorReturn(false);
    }

    @Override
    @NonNull
    public Publisher<Fruit> list() {
        return getCollection().find();
    }

    @NonNull
    private MongoCollection<Fruit> getCollection() {
        return mongoClient.getDatabase(mongoConf.getName())
                .getCollection(mongoConf.getCollection(), Fruit.class);
    }
}