package proiectdiz.ShamirScheme;

import java.math.BigInteger;

public class Fraction {
    private BigInteger numinator;
    private BigInteger denuminator;

    public Fraction(BigInteger num, BigInteger den){
        this.numinator=num;
        this.denuminator=den;

    }

    public BigInteger getNuminator() {
        return numinator;
    }

    public BigInteger getDenuminator() {
        return denuminator;
    }

    public void setNuminator(BigInteger numinator) {
        this.numinator = numinator;
    }

    public void setDenuminator(BigInteger denuminator) {
        this.denuminator = denuminator;
    }

    public void reduceFraction(Fraction fraction){
        BigInteger gcd=fraction.getNuminator().gcd(fraction.getDenuminator());

        fraction.setNuminator(fraction.getNuminator().divide(gcd));
        fraction.setDenuminator(fraction.getDenuminator().divide(gcd));
    }
    public void MultiplyOp(Fraction f,BigInteger p){
        Fraction temp= new Fraction(numinator.multiply(f.getNuminator()),denuminator.multiply(f.getDenuminator()));
        temp.reduceFraction(temp);
        this.setDenuminator(temp.getDenuminator());
        this.setNuminator(temp.getNuminator().mod(p));


    }
    public void Add(Fraction f,BigInteger p){
        Fraction temp= new Fraction(numinator.multiply(f.getDenuminator()).add(denuminator.multiply(f.getNuminator())),denuminator.multiply(f.getDenuminator()));
        temp.reduceFraction(temp);
        this.setNuminator(temp.getNuminator().mod(p));
        this.setDenuminator(temp.getDenuminator());

    }
}
