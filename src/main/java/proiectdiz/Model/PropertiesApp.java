package proiectdiz.Model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesApp {

    @Value("myapp.n")
    private String n;
    @Value("myapp.l")
    private String l;

    public String getN() {
        return n;
    }

    public String getL() {
        return l;
    }
}
