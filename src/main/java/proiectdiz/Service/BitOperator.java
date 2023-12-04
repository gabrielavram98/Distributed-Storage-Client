package proiectdiz.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

public class BitOperator {
    public static void Handle(String Secret){
        byte[] bytearray=Secret.getBytes();
        int size=bytearray.length;



    }
    public static BigInteger generatePrime(int bitLength) {
        SecureRandom random = new SecureRandom();

        BigInteger p;
        do {
            // Generate a probable prime of the specified bit length
            p = new BigInteger(bitLength, 100, random);

            //BigInteger probable_prime=BigInteger.probablePrime(bitLenght,random);
        } while (!p.isProbablePrime(100));

        return p;
    }
}
