package controller;

import dtos.Fruit;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import repository.FruitRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.http.HttpStatus.CONFLICT;
import static io.micronaut.http.HttpStatus.CREATED;

@Controller("/fruits")
class FruitController {

    private final FruitRepository fruitService;

    FruitController(FruitRepository fruitService) {
        this.fruitService = fruitService;
    }

    @Get
    Publisher<Fruit> list() {
        return fruitService.list();
    }

    @Post
    Mono<HttpStatus> save(@NonNull @NotNull @Valid Fruit fruit) {
        return fruitService.save(fruit)
                .map(added -> added ? CREATED : CONFLICT);
    }
}