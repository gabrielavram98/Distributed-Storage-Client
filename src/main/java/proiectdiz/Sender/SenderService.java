package proiectdiz.Sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SenderService {

    private final WebClient webClient;

    @Autowired
    public SenderService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3000").build(); // Replace with the actual base URL of the receiving application
    }

    public void sendJsonToReceiver(String jsonValue) {
        webClient.post()
                .uri("/api/server1")
                .body(BodyInserters.fromValue(jsonValue))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(); // If you want to consume the response, otherwise you can omit this line
    }
}
