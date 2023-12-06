package proiectdiz.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
public class Polynom {
    private BigInteger[] coefficients;

    private BigInteger[] signedCoefficients;
    private int[] sign;
    private int n;
    private int k;
    private BigInteger p;

    private final BigInteger[] x;
    private final BigInteger[] y;
    private final BigInteger secret;
    int bitlength;

    public Polynom(BigInteger[] coeficients, BigInteger p, int n, int k ,BigInteger secret,int bitlen){
        this.coefficients=coeficients;
        this.p=p;

        this.bitlength=bitlen;
        this.n=n;
        this.k=k;
        this.secret=secret;
        sign= new int[k];
        signedCoefficients= new BigInteger[coeficients.length];
        x= new BigInteger[this.n];
        y= new BigInteger[this.n];

        signs();
        SetSignToCoefficients();


    }
    private void signs(){
        Random random = new Random();
        for(int i=0;i<k;i++){
            if(random.nextInt()%2==0){
                sign[i]=1;
            }
            else{
                sign[i]=-1;
            }
        }
    }
    public void generateX(){
        for(int i=0;i<n;i++){

            x[i] =  new BigInteger(bitlength, new Random()).mod(p);
        }
    }

    public void generateY(){
        for(int i=0;i<n;i++){
            y[i]=new BigInteger("0");
            for (int j=k-1;j>0;j--) {

                BigInteger rez= x[i].modPow(new BigInteger(String.valueOf(j)), p);
                rez=rez.multiply(signedCoefficients[j-1]);
                rez=rez.mod(p);
                y[i] = y[i].add(rez);
                y[i]=y[i].mod(p);
            }//TODO:WHAT THE FUCK
            y[i]=y[i].add(secret.multiply(new BigInteger(String.valueOf(sign[0]))));

        }
    }
    private void SetSignToCoefficients(){
        for(int i=0;i<coefficients.length;i++){
            signedCoefficients[i]=coefficients[i].multiply(new BigInteger(String.valueOf(sign[i])));
        }
    }

    public BigInteger[] getX() {
        return x;
    }

    public BigInteger[] getY() {
        return y;
    }
}
