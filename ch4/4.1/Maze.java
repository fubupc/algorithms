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
        rows = r + 2;
        cols = c + 2;

        marked = new boolean[rows][cols];
        east = new boolean[rows][cols];
        south = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            marked[i][0] = true;
            marked[i][cols - 1] = true;
            south[i][0] = true;
            south[i][cols - 1] = true;
        }

        for (int j = 0; j < cols; j++) {
            marked[0][j] = true;
            marked[rows - 1][j] = true;
            east[0][j] = true;
            east[rows - 1][j] = true;
        }

        dfs(1, 1);
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

        if (!marked[x][y + 1]) {
            l.add(E);
        }

        if (!marked[x + 1][y]) {
            l.add(S);
        }

        if (!marked[x][y - 1]) {
            l.add(W);
        }

        if (!marked[x - 1][y]) {
            l.add(N);
        }

        return l;
    }

    @Override
    public String toString() {
        String out = "";
        String chars = " ╴╷┐╶─┌┬╵┘│┤└┴├┼";

        int t, r, b, l;  // if top, right, bottom, left has bar.
       
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                t = toInt(!east[row - 1][col - 1]);
                r = toInt(!south[row - 1][col]);
                b = toInt(!east[row][col - 1]);
                l = toInt(!south[row - 1][col - 1]);

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

    public void walk(int startX, int startY, int endX, int endY) {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                marked[rows][cols] = false;
            }
        }

        explore(startX, startY, endX, endY);
    }

    public void explore(int x, int y, int endX, int endY) {
        marked[x][y] = true;

        if (x == endX && y == endY) return;

        if (!marked[x + 1][y]) {
            explore(x + 1, y, endX, endY);
        }

        if (!marked[x][y + 1]) {
            explore(x, y + 1, endX, endY);
        }

        if (!marked[x - 1][y]) {
            explore(x - 1, y, endX, endY);
        }

        if (!marked[x][y - 1]) {
            explore(x, y - 1, endX, endY);
        }
    }

    public void draw() {
        StdDraw.setScale(-0.05, 1.05);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLUE);

        double ru = 1.0 / (rows - 2);
        double cu = 1.0 / (cols - 2);

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (!east[i][j - 1]) {
                    StdDraw.line((j - 1) * cu, (rows - i - 1) * ru, (j - 1) * cu, (rows - i - 2) * ru);
                }

                if (!south[i - 1][j]) {
                    StdDraw.line((j - 1) * cu, (rows - i - 1) * ru, j * cu, (rows - i - 1) * ru);
                }
            }
        }

        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
    }

    public static void main(String[] args) {
        Maze m = new Maze(20, 20);
        System.out.println(m);
        m.draw();
    }

}
