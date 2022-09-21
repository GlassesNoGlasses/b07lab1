public class Polynomial {
    double [] coefficents;

    public Polynomial() {
        coefficents = new double[1];
    }

    public Polynomial(double[] arr) {
        int len = arr.length;
        if(len == 0) {
            coefficents = new double[1];
        }
        else {
            coefficents = new double[arr.length];
            for(int i = 0; i < arr.length; i++) {
                coefficents[i] = arr[i];
            }
        }
    }

    public Polynomial add(Polynomial p) {
        int l1 = this.coefficents.length;
        int l2 = p.coefficents.length;
        int max = Math.max(l1, l2);
        int min = Math.min(l1, l2);
        double [] arr = new double[max];
        Polynomial q = new Polynomial(arr);
        for(int i = 0; i < min; i++) {
            q.coefficents[i] = this.coefficents[i] + p.coefficents[i];
        }
        if (l1 > l2) {
            for(int j = min; j < max; j++) {
                q.coefficents[j] = this.coefficents[j];
            }
        }
        else if (l2 > l1) {
            for(int j = min; j < max; j++) {
                q.coefficents[j] = p.coefficents[j];
            }
        }
        return q;
    }

    public double evaluate(double x) {
        double num = coefficents[0];
        for(int i = 1; i < coefficents.length; i++) {
            num = num + (coefficents[i] * (Math.pow(x, i)));
        }
        return num;
    }

    public Boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }
}
