package proiectdiz.Service;

import proiectdiz.Model.Properties;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

public class SecretDevider {
    public static List<String>Devider(byte[] secret){
        Properties properties= new Properties();
        List<BigInteger> parts = new ArrayList<BigInteger>();
        BigInteger BigIntegerSecret= new BigInteger(secret);
        int n=properties.getN();
        BigInteger p=BitOperator.generatePrime(128);
        System.out.println(p.toString());

        return  null;

    }
}
