package proiectdiz.Service;
import org.springframework.web.reactive.function.client.WebClient;
import proiectdiz.Helpers.Properties;
import proiectdiz.Log.Log;

public class HeathService {
    private  static String apiUrl = "http://localhost:443/api/server";
    public static int GetNumberOfUpServers(){
        int nr_of_servers = 0;
        try {
            WebClient webClient = WebClient.create();
            if (!Properties.getAvailableServers().isEmpty()) {
                Properties.getAvailableServers().clear();
            }


            for (int i = 1; i <= 7; i++) {

                String responseData = webClient.get()
                        .uri(apiUrl + i + "/health")
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
                if (responseData.contains("Up")) {
                    nr_of_servers++;
                    Properties.AddServers("server" + i);
                }

            }
        }
        catch (Exception e){
            Log.ErrorLog(e.getMessage());
            nr_of_servers =-1;
        }

        return nr_of_servers;

    }
}
