package proiectdiz.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Properties {
    @Value("${app.n}")
    private String n;

    @Value("${app.l}")
    private String l;

    public int getN() {
        return Integer.parseInt(n);
    }

    public int getL() {
        return Integer.parseInt(l);
    }
}
