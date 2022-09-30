import java.io.File;

public class Driver {
    public static void main(String [] args) {
        double[] arr = {1, -2};
        double[] arr2 = {-4, 1, 5};
        int[] exp1 = {2, 1, 6};
        Polynomial p1 = new Polynomial(arr);
        Polynomial p2 = new Polynomial(arr2, exp1);
        Polynomial p3 = p1.multiply(p2);
        File f = new File("test.txt");
        Polynomial p4 = new Polynomial(f);
        p3.saveToFile("test2.txt");
    } }
