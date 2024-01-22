package proiectdiz.Sender;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.LockEelement;
import proiectdiz.Helpers.ValidationCheck;
import proiectdiz.Log.Log;
import proiectdiz.Model.QuantecKey;
import reactor.core.publisher.Mono;

@Service
public class KeyRequestService {

    private final WebClient KeyRequestClient;

    @Autowired
    public KeyRequestService(WebClient.Builder KeyRequestBuilder){
        this.KeyRequestClient=KeyRequestBuilder.baseUrl("http://localhost:44316")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    ;
    public void getStatus(String jsonValue) {
        Object mapper=
        KeyRequestClient.get().uri("/org_1_alice/api/v1/keys/org_2_bob/status")

                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> {

                            System.out.println("Response received: " + response);

                        },
                        error -> {

                            System.err.println("Error occurred: " + error.getMessage());
                        },
                        () -> {

                            System.out.println("Request completed");
                        }
                );
    }

    public String getKeys(String jsonValue, String destination) {
        return KeyRequestClient.post().uri(destination)
                .body(BodyInserters.fromValue(jsonValue))
                .retrieve()
                .bodyToMono(String.class)
                .block();



    }





    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response status: " + clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientResponse);
        });
    }









}
