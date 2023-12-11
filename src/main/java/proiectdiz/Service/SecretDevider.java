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

    public  Polynom Devide(byte[] secret){
        Properties properties= new Properties();
        int n=7;
        int k=5;
        List<BigInteger> parts = new ArrayList<BigInteger>();
        BigInteger BigIntegerSecret= new BigInteger(secret);



        System.out.println("Secret:"+BigIntegerSecret);
        /*
       // byte[] secretBiginteger= BigIntegerSecret.toByteArray();
       // String Secret= secretBiginteger.toString();
       //
        // int n=properties.getN();
        */

        BigInteger p=BitOperator.generatePrimeP(BigIntegerSecret.bitLength()+1);
        BigInteger[] coeficients=BitOperator.generateCoeficientsModP(p,k,p.bitLength());

        //System.out.println(p.toString());

        return pointGenerator(coeficients,n,k,p,256, BigIntegerSecret);

    }

    public Polynom pointGenerator(BigInteger[] coeficients, int n,int k, BigInteger p, int bitlength, BigInteger secret){
            Polynom polynom = new Polynom(coeficients,p,n,k,secret,bitlength);
            polynom.generateX();
            polynom.generateY();
            return polynom;





    }

}
