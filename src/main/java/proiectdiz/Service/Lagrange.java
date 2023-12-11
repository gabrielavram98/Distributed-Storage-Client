package proiectdiz.Service;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.analysis.solvers.PolynomialSolver;
import java.math.BigInteger;

public class Lagrange {
    private BigInteger[] x;
    private BigInteger[] y;
    private int k;
    BigInteger p;

    public Lagrange(BigInteger[] x, BigInteger[] y, BigInteger p, int k){
        this.x=x;
        //this.x = new BigInteger[]{BigInteger.valueOf(20), BigInteger.valueOf(23), BigInteger.valueOf(21),BigInteger.valueOf(13)};
        //this.y = new BigInteger[]{BigInteger.valueOf(1554), BigInteger.valueOf(1542), BigInteger.valueOf(1510),BigInteger.valueOf(1582)};
        //SECRET IS 1634
        this.y=y;
        this.p=p;
        this.k=k;

    }

    public BigInteger lagrangeInterpolation() {

/*
        Fraction ans= new Fraction(new BigInteger("0"),new BigInteger("1"));

        for (int i = 0; i < k; i++) {

            Fraction L= new Fraction(y[i],new BigInteger("1"));
            for (int j = 0; j < k; j++) {
                if (j != i) {
                        Fraction temp= new Fraction(x[j].multiply(new BigInteger("-1")),x[i].subtract(x[j]));
                        L.MultiplyOp(temp,p);

                }
            }
            ans.Add(L,p);
        }

        return ans.getNuminator();
    }
    */

        BigInteger accum = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    num = num.multiply(BigInteger.valueOf(-j - 1)).mod(p);
                    den = den.multiply(BigInteger.valueOf(i - j)).mod(p);
                }
            }


            final BigInteger value =y[i];// shares[i].getShare();

            final BigInteger tmp = value.multiply(num).multiply(den.modInverse(p)).mod(p);
            accum = accum.add(p).add(tmp).mod(p);


        }

        System.out.println("The secret is: " + accum);

        return accum;

    }
}
