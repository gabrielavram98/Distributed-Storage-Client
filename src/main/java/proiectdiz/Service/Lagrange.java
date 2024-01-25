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
        //this.x = new BigInteger[]{BigInteger.valueOf(20), BigInteger.valueOf(23), BigInteger.valueOf(21),BigInteger.valueOf(13)};
        //this.y = new BigInteger[]{BigInteger.valueOf(1554), BigInteger.valueOf(1542), BigInteger.valueOf(1510),BigInteger.valueOf(1582)};
        //SECRET IS 1634
        this.y=y;
        this.p=p;
        this.k=k;

    }

    public BigInteger lagrangeInterpolation() {

        List<BigInteger> shuffle_list_x=  new ArrayList<>(Arrays.asList(x));
        List<BigInteger> shuffle_list_y=  new ArrayList<>(Arrays.asList(y));
        BigInteger x2=shuffle_list_x.get(2);
        shuffle_list_x.set(2,shuffle_list_x.get(3));
        shuffle_list_x.set(3,x2);
        BigInteger y2=shuffle_list_y.get(2);
        shuffle_list_y.set(2,shuffle_list_y.get(3));
        shuffle_list_y.set(3,y2);

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
