import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String result = "";
        while (!StdIn.isEmpty()) {
            i += 1;
            double p = 1.0 / i;
            String value = StdIn.readString();
            boolean keep = StdRandom.bernoulli(p);

            if (keep) {
                result = value;
            }
        }
        StdOut.println(result);
    }
}
