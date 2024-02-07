package proiectdiz.ShamirScheme;

import proiectdiz.Helpers.Properties;

import java.math.BigInteger;
import java.util.*;

import proiectdiz.Validation.BitOperator;

public class SecretDevider {
    int n;
    int k;
    BigInteger[] coeficients;
    BigInteger p;
    public SecretDevider(BigInteger p){

        n = Properties.getN();
        k=Properties.getL();
        coeficients= BitOperator.generateCoeficientsModP(p,k,p.bitLength());
        this.p=p;

    }

    public Polynom Devide(byte[] secret){


        BigInteger BigIntegerSecret= new BigInteger(secret);
        Polynom polynom = new Polynom(coeficients,p,n,k,BigIntegerSecret,BigIntegerSecret.bitLength()+1);
        polynom.generateX();
        polynom.generateY();
        polynom.setGuid(UUID.randomUUID());
        return polynom;

    }



}
