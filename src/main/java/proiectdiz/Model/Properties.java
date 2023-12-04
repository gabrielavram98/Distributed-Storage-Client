package proiectdiz.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class Properties {

    @Value("${myapp.n}")
    private String n;


    @Autowired
    private Environment env;

    @Value("${myapp.l}")
    private String l;

    public int getN() {
        System.out.println(n);
        return Integer.parseInt(n);
    }

    public int getL() {
       return Integer.parseInt(l);
    }
}
