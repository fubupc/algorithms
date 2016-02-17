import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<String>();

        int count = 0;

        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            count++;

            if (count <= k) {
                q.enqueue(next);
            } else {
                int rand = StdRandom.uniform(1, count + 1);

                if (rand <= k) {
                    q.dequeue();
                    q.enqueue(next);
                }
            }
        }

        for (String s : q) {
            StdOut.println(s);
        }
    }

}


