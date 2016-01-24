/* Perfect maze generator
 */
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int rows;
    private int cols;

    private boolean[][] marked;
    private boolean[][] east;
    private boolean[][] south;

    public static final int E = 1;
    public static final int S = 2;
    public static final int W = 3;
    public static final int N = 4;

    public Maze(int r, int c) {
        rows = r;
        cols = c;

        marked = new boolean[rows][cols];
        east = new boolean[rows][cols];
        south = new boolean[rows][cols];

        dfs(0, 0);
    }

    public void dfs(int x, int y) {
        marked[x][y] = true;

        while (true) {
            List<Integer> l = adj(x, y);

            if (l.size() == 0) return;

            int rand = (int) (Math.random() * l.size());
            int next = l.get(rand);

            if (next == E) {
                east[x][y] = true;
                dfs(x, y + 1);
            } else if (next == S) {
                south[x][y] = true;
                dfs(x + 1, y);
            } else if (next == W) {
                east[x][y - 1] = true;
                dfs(x, y - 1);
            } else {
                south[x - 1][y] = true;
                dfs(x - 1, y);
            }
        }
    }

    public List<Integer> adj(int x, int y) {
        List<Integer> l = new ArrayList<Integer>();

        if (y != cols - 1 && !marked[x][y + 1]) {
            l.add(E);
        }

        if (x != rows - 1 && !marked[x + 1][y]) {
            l.add(S);
        }

        if (y != 0 && !marked[x][y - 1]) {
            l.add(W);
        }

        if (x != 0 && !marked[x - 1][y]) {
            l.add(N);
        }

        return l;
    }

    @Override
    public String toString() {
        String out = "";
        String chars = " ╴╷┐╶─┌┬╵┘│┤└┴├┼";

        int t, r, b, l;  // if top, right, bottom, left has bar.
       
        for (int row = 0; row < rows + 1; row++) {
            for (int col = 0; col < cols + 1; col++) {
                if (row == 0) {
                    t = 0;
                } else if (col == 0) {
                    t = 1;
                } else {
                    t = toInt(!east[row - 1][col - 1]);
                }

                if (col == cols) {
                    r = 0;
                } else if (row == 0) {
                    r = 1;
                } else {
                    r = toInt(!south[row - 1][col]);
                }

                if (row == rows) {
                    b = 0;
                } else if (col == 0) {
                    b = 1;
                } else {
                    b = toInt(!east[row][col - 1]);
                }

                if (col == 0) {
                    l = 0;
                } else if (row == 0) {
                    l = 1;
                } else {
                    l = toInt(!south[row - 1][col - 1]);
                }

                int index = (t << 3) + (r << 2) + (b << 1) + l;
                out += chars.charAt(index);
            }

            out += "\n";
        }

        return out;
    }

    public int toInt(boolean b) {
        if (b == true) return 1;
        else return 0;
    }


    public static void main(String[] args) {
        Maze m = new Maze(10, 10);
        System.out.println(m);
    }

}
