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
    private BigInteger secret;
    private final Random r= new Random();
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

        //signs();
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
        for (int i = 0; i < k - 1; i++) {
            x[i] = randomZp(p);
            System.out.println("X:"+ i +":"+x[i]);

        }
    }

    public void generateY(){

        for (int i = 1; i <= n; i++) {
            BigInteger accum = secret.mod(p);

            for (int j = 1; j < k; j++) {
                final BigInteger t1 = BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), p);
                final BigInteger t2 = coefficients[j - 1].multiply(t1).mod(p);

                accum = accum.add(t2).mod(p);
            }
            y[i - 1] = accum;
            System.out.println("Y:"+ i +":"+y[i-1]);

        }


    }
    private void SetSignToCoefficients(){
        for(int i=0;i<coefficients.length;i++){
            signedCoefficients[i]=coefficients[i];//.multiply(new BigInteger(String.valueOf(sign[i])));
        }
    }

    public BigInteger[] getX() {
        return x;
    }

    public BigInteger[] getY() {
        return y;
    }
    public BigInteger getP(){
        return  p;
    }

    public int getK() {
        return k;
    }
    public BigInteger randomZp(final BigInteger p) {
        while (true) {
            final BigInteger r = new BigInteger(p.bitLength(), new Random());
            if (r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(p) < 0) {
                return r;
            }
        }
    }
}
