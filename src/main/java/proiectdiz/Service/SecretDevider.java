package proiectdiz.Service;

import proiectdiz.Model.Properties;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

public class SecretDevider {
    int n;
    int k;
    BigInteger[] coeficients;
    BigInteger p;
    public SecretDevider(BigInteger p){

        n = Properties.getN();
        k=Properties.getL();
        coeficients=BitOperator.generateCoeficientsModP(p,k,p.bitLength());
        this.p=p;

    }

    public  Polynom Devide(byte[] secret){


        BigInteger BigIntegerSecret= new BigInteger(secret);
        Polynom polynom = new Polynom(coeficients,p,n,k,BigIntegerSecret,BigIntegerSecret.bitLength()+1);
        polynom.generateX();
        polynom.generateY();
        polynom.setGuid(UUID.randomUUID());
        return polynom;

    }



}
