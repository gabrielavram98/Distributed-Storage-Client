package proiectdiz.Validation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class BitOperator {
    public static void Handle(String Secret){
        byte[] bytearray=Secret.getBytes();
        int size=bytearray.length;



    }
    public static BigInteger generatePrimeP(int bitLength) {
        SecureRandom random = new SecureRandom();
      //  long startTime = System.currentTimeMillis();
        //BigInteger p= BigInteger.probablePrime(bitLength, new Random());
       // if(bitLength<60){
        //    bitLength=60;
       // }
        BigInteger p;
        do {

            p = new BigInteger(bitLength, 100, random);

     //       BigInteger probable_prime=BigInteger.probablePrime(bitLenght,random);
        } while (!p.isProbablePrime(100));
       // long endTime = System.currentTimeMillis();
       // long time=endTime-startTime;
       // long timeinms=time/1000;
        return p;
    }
    public static BigInteger[] generateCoeficientsModP(BigInteger p,int k, int bitlength){
        BigInteger[] coeficients= new BigInteger[k-1];
        for(int i=0;i<k-1;i++){


            coeficients[i]=new BigInteger(bitlength, new Random()).mod(p);
            if(coeficients[i].compareTo(BigInteger.ZERO)<=0){
                i--;
            }
        }
        return coeficients;
    }

}
