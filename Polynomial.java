import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    double[] coefficents;
    int[] exponents;

    public Polynomial() {
        coefficents = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] arr) {
        int len = arr.length;
        if (len == 0) {
            coefficents = new double[1];
            exponents = new int[1];
        } else {
            coefficents = new double[len];
            exponents = new int[len];
            for (int i = 0; i < len; i++) {
                exponents[i] = i;
                coefficents[i] = arr[i];
            }
        }
    }

    public Polynomial(double[] arr, int[] exp) {
        int len = arr.length;
        if (len == 0) {
            coefficents = new double[1];
            exponents = new int[1];
        } else {
            coefficents = new double[len];
            exponents = new int[len];
            for (int i = 0; i < len; i++) {
                exponents[i] = exp[i];
                coefficents[i] = arr[i];
            }
        }
    }

    public Polynomial(File txt) {
        Scanner input;
        try {
            input = new Scanner(txt);
            String poly = input.nextLine();
            String[] s = poly.split("(?=-)|\\+");
            int len = s.length;
            coefficents = new double[len];
            exponents = new int[len];
            for (int i = 0; i < len; i++) {
                String[] sep = s[i].split("x");
                if (s[i].charAt(0) == 'x') {
                    coefficents[i] = 1;
                } else if (s[i].contains("-x")) {
                    coefficents[i] = -1;
                } else {
                    coefficents[i] = 1 * Double.parseDouble(sep[0]);
                }
                if (sep.length > 1) {
                    exponents[i] = Integer.parseInt(sep[1]);
                } else if (sep.length == 1 && s[i].contains("x")) {
                    exponents[i] = 1;
                } else {
                    exponents[i] = 0;
                }
            }
        } catch (FileNotFoundException e) {
            coefficents = new double[1];
            exponents = new int[1];
        }
    }

    public Polynomial add(Polynomial p) {
        int count = 0;
        int l1 = this.coefficents.length;
        int l2 = p.coefficents.length;
        int m = uniqueItems(p.exponents, this.exponents);
        double[] arr = new double[m];
        Polynomial q = new Polynomial(arr);
        for (int i = 0; i < l1; i++) {
            q.exponents[i] = this.exponents[i];
            q.coefficents[i] = this.coefficents[i];
        }

        for (int j = 0; j < l2; j++) {
            int idx = -1;
            int expn = p.exponents[j];
            double coeff = p.coefficents[j];
            for (int k = 0; k < l1; k++) {
                if (expn == q.exponents[k]) {
                    idx = k;
                    break;
                }
            }
            if (idx != -1) {
                q.coefficents[idx] = q.coefficents[idx] + coeff;
            } else {
                q.exponents[l1 + count] = expn;
                q.coefficents[l1 + count] = coeff;
                count++;
            }
        }
        return q;
    }

    public double evaluate(double x) {
        double num = coefficents[0] * (Math.pow(x, exponents[0]));
        int len = coefficents.length;
        for (int i = 1; i < len; i++) {
            num = num + (coefficents[i] * (Math.pow(x, exponents[i])));
        }
        return num;
    }

    public Boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial p) {
        int max = maxItem(this.exponents) + maxItem(p.exponents);
        Polynomial q = new Polynomial(new double[max + 1]);

        int l1 = this.exponents.length;
        int l2 = p.exponents.length;
        int count = 1;
        for (int i = 0; i < l1; i++) {
            int exp = this.exponents[i];
            double coeff = this.coefficents[i];
            for (int j = 0; j < l2; j++) {
                int exp2 = p.exponents[j];
                double coeff2 = p.coefficents[j];
                int ilst = inList(q.exponents, exp + exp2);
                if (ilst != -1) {
                    q.coefficents[ilst] = q.coefficents[ilst] + (coeff * coeff2);
                } else {
                    q.coefficents[count] = coeff * coeff2;
                    q.exponents[count] = q.exponents[count] + exp + exp2;
                    count++;
                }
            }
        }
        return q;
    }


    public void saveToFile(String file) {
        String s = new String();
        int len = this.coefficents.length;
        for(int i = 0; i < len; i++) {
            if(this.coefficents[i] == 0) {
                continue;
            }
            if (coefficents[i] > 0 && s != null)  s += "+";
            s += Double.toString(this.coefficents[i]);
            if(this.exponents[i] >= 1) s += "x";
            if (this.exponents[i] > 1)  s += Integer.toString(this.exponents[i]);
        }
        if(s.length() == 0) s = s + "0";
        try {
            File f = new File(file);
            f.createNewFile();
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int inList(int[] base, int search) {
        int len = base.length;
        for (int i = 0; i < len; i++) {
            if (base[i] == search)
                return i;
        }
        return -1;
    }

    private int maxItem(int[] arr) {
        int len = arr.length;
        if (len == 0) {
            return 0;
        }
        int count = arr[0];
        for (int i : arr) {
            if (count < i) {
                count = i;
            }
        }
        return count;
    }

    private int uniqueItems(int[] exp1, int[] exp2) {
        int l1 = exp1.length;
        int l2 = exp2.length;
        int m = l1;
        for (int i = 0; i < l2; i++) {
            int n = exp2[i];
            Boolean exist = false;
            for (int j = 0; j < l1; j++) {
                if (n == exp1[j]) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                m++;
            }
        }

        return m;
    }
}
