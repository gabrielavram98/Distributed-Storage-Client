package proiectdiz.Service;
//import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
//import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
//import org.apache.commons.math3.analysis.solvers.PolynomialSolver;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lagrange {
    private BigInteger[] x;
    private BigInteger[] y;
    private int k;
    BigInteger p;

    public Lagrange(BigInteger[] x, BigInteger[] y, BigInteger p, int k){
        this.x=x;

        this.y=y;
        this.p=p;
        this.k=k;

    }

    public BigInteger lagrangeInterpolation() {



        BigInteger accum = BigInteger.ZERO;
        for (int i = 0; i < k; i++) {
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    num = num.multiply(x[j].multiply(BigInteger.valueOf(-1))).mod(p);
                    den = den.multiply(x[i].subtract(x[j])).mod(p);
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
