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
        Properties properties= new Properties();
        n=7;
        k=5;
        coeficients=BitOperator.generateCoeficientsModP(p,k,p.bitLength());
        this.p=p;

    }

    public  Polynom Devide(byte[] secret){

        List<BigInteger> parts = new ArrayList<BigInteger>();
        BigInteger BigIntegerSecret= new BigInteger(secret);



        System.out.println("Secret:"+BigIntegerSecret);
        /*
       // byte[] secretBiginteger= BigIntegerSecret.toByteArray();
       // String Secret= secretBiginteger.toString();
       //
        // int n=properties.getN();
        */
        //BigInteger p= new BigInteger("8179931315112863805513837519301825259317163714001390935596696969971767919105186246500545495233727286288593745665614474514507902638447856085527919595017171");
        //BigInteger p=BitOperator.generatePrimeP(BigIntegerSecret.bitLength()+1);



        //System.out.println(p.toString());

        return pointGenerator(coeficients,n,k,p,BigIntegerSecret.bitLength()+1, BigIntegerSecret);

    }

    public Polynom pointGenerator(BigInteger[] coeficients, int n,int k, BigInteger p, int bitlength, BigInteger secret){
            Polynom polynom = new Polynom(coeficients,p,n,k,secret,bitlength);
            polynom.generateX();
            polynom.generateY();
            polynom.setGuid(UUID.randomUUID());
            return polynom;





    }

}
