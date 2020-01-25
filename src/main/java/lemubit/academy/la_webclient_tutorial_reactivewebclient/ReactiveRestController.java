package lemubit.academy.la_webclient_tutorial_reactivewebclient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
public class ReactiveRestController {


    //region ...
    String url = "http://localhost:8080";
    WebClient webClient = WebClient.builder().baseUrl(url).build();
    //endregion

    @GetMapping("/person/{id1}/{id2}/{id3}")
    public String getPerson(@PathVariable int id1, @PathVariable int id2, @PathVariable int id3) {
        getPersonWithTemplate(id1, id2, id3);
        return "Processing with RestTemplate...";
    }

    @GetMapping("/personR/{id1}/{id2}/{id3}")
    public String getPersonReactive(@PathVariable int id1, @PathVariable int id2, @PathVariable int id3) {
        getPersonWithReactive(id1, id2, id3);
        return "Processing with WebClient...";
    }

    void getPersonWithTemplate(int id1, int id2, int id3) {
        System.out.println("T.............");
        final var uri = "http://localhost:8080/person/{id}";
        var restTemplate = new RestTemplate();

        //First Call
        var params = new HashMap<String, Integer>();
        params.put("id", id1);
        var result = restTemplate.getForObject(uri, Person.class, params);
        System.out.println(result);

        //Second Call
        var params2 = new HashMap<String, Integer>();
        params2.put("id", id2);
        var result2 = restTemplate.getForObject(uri, Person.class, params2);
        System.out.println(result2);

        //Third Call
        var params3 = new HashMap<String, Integer>();
        params3.put("id", id3);
        var result3 = restTemplate.getForObject(uri, Person.class, params3);
        System.out.println(result3);
    }

    void getPersonWithReactive(int id1, int id2, int id3) {
        System.out.println("R.............");

        //First Call
        webClient.get()
                .uri("/person/{id}", id1)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Person.class)
                .subscribe(System.out::println);

        //Second Call
        webClient.get()
                .uri("/person/{id}", id2)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Person.class)
                .subscribe(System.out::println);

        //Third Call
        webClient.get()
                .uri("/person/{id}", id3)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Person.class)
                .subscribe(System.out::println);
    }

    @PostMapping("/person")
    public Person addPerson() {
        Person person = new Person(7, "Lemubit", 11, "World");

        webClient.post()
                .uri("/person")
                .body(Mono.just(person), Person.class)
                .exchange()
                .subscribe(clientResponse -> System.out.println(clientResponse.statusCode()));

        return person;
    }

    @GetMapping("/people")
    public String getPeople() {
        webClient.get()
                .uri("/people")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Person.class)
                .subscribe(person -> {
                    System.out.println("People::::::-----");
                    System.out.println(person);
                });

        return "Done";

    }


}
