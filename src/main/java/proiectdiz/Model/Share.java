package proiectdiz.Model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Share {
    private String GUID;
    private List<BigInteger> X;
    private List<BigInteger> Y;
    public Share(){
        X= new ArrayList<>();
        Y= new ArrayList<>();
    }
    public void Add2X(String x ){
         this.X.add(new BigInteger(x));
    }
    public void Add2Y(String y){
         this.Y.add(new BigInteger(y));
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getGUID() {
        return GUID;
    }

    public List<BigInteger> getX() {
        return X;
    }

    public List<BigInteger> getY() {
        return Y;
    }
}
