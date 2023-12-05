package proiectdiz.Service;

import proiectdiz.Model.Properties;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

public class SecretDevider {

    public  BigInteger[][] Devide(byte[] secret){
        Properties properties= new Properties();
        int n=4;
        int k=3;
        List<BigInteger> parts = new ArrayList<BigInteger>();
        BigInteger BigIntegerSecret= new BigInteger(secret);
        /*
       // byte[] secretBiginteger= BigIntegerSecret.toByteArray();
       // String Secret= secretBiginteger.toString();
       //
        // int n=properties.getN();
        */

        BigInteger p=BitOperator.generatePrimeP(4096);
        BigInteger[] coeficients=BitOperator.generateCoeficientsModP(p,k,4000);

        System.out.println(p.toString());

        return pointGenerator(coeficients,n,k,p,4000);

    }

    public BigInteger[][] pointGenerator(BigInteger[] coeficients, int n,int k, BigInteger p, int bitlength){
        BigInteger[][] points= new BigInteger[n][2];

        for(int i=0;i<n;i++){
            //x
             points[i][0]=  new BigInteger(bitlength, new Random()).mod(p);
             for(int j=0;j<k-1;j++){

                  //y
                 points[i][1]=points[i][1].add(points[i][0].modPow(new BigInteger(String.valueOf(j)),p));
                 //TODO : GENERATE POLYNOM USING A RANDOM SIGN GENERATOR AND SOLVE IT
             }

        }
        return points;

    }
}
