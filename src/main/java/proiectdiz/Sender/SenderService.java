package proiectdiz.Sender;
import proiectdiz.Helpers.JsonHandler;
import proiectdiz.Helpers.ValidationCheck;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SenderService {

    private final WebClient webClient;

    @Autowired
    public SenderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    public void sendJsonToReceiver(String jsonValue,String destination) {

            webClient.post().uri(destination)
                    .body(BodyInserters.fromValue(jsonValue))
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            response -> {

                                System.out.println("Response received: " + response);

                                ///try {
                               // if (ValidationCheck.Validate(JsonHandler.StringToJson(response), "src\\main\\resources\\RequestSchema.json") != 0) {

                            //        throw new Exception("Error in Validating the request" + JsonHandler.StringToJson(response));
                           //     }
                           //         } catch (Exception e) {
                           //             throw new RuntimeException(e);
                           //         }
                            },
                            error -> {

                                System.err.println("Error occurred: " + error.getMessage());
                            },
                            () -> {

                                System.out.println("Request completed");
                            }
                    );

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
