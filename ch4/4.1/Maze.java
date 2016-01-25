/* Perfect maze generator
*/
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private int N;

    private boolean[][] marked;

    private boolean[][] north;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] east;

    public static final int N2 = 1;
    public static final int S = 2;
    public static final int W = 3;
    public static final int E = 4;

    private boolean done;

    public Maze(int N) {
        this.N = N;

        marked = new boolean[N + 2][N + 2];

        north = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];

        for (int x = 0; x < N + 2; x++) {
            for (int y = 0; y < N + 2; y++) {
                if (x == 0 || x == N + 1 || y == 0 || y == N + 1) {
                    marked[x][y] = true;
                } else {
                    north[x][y] = true;
                    south[x][y] = true;
                    west[x][y] = true;
                    east[x][y] = true;
                }
            }
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

            if (next == N2) {
                north[x][y] = false;
                south[x][y + 1] = false;

                dfs(x, y + 1);
            } else if (next == S) {
                south[x][y] = false;
                north[x][y - 1] = false;

                dfs(x, y - 1);
            } else if (next == W) {
                west[x][y] = false;
                east[x - 1][y] = false;

                dfs(x - 1, y);
            } else if (next == E) {
                east[x][y] = false;
                west[x + 1][y] = false;

                dfs(x + 1, y);
            } 
        }
    }

    public List<Integer> adj(int x, int y) {
        List<Integer> l = new ArrayList<Integer>();

        if (!marked[x][y + 1]) l.add(N2);
        if (!marked[x][y - 1]) l.add(S);
        if (!marked[x + 1][y]) l.add(E);
        if (!marked[x - 1][y]) l.add(W);

        return l;
    }

    @Override
    public String toString() {
        String out = "";
        String chars = " ╴╷┐╶─┌┬╵┘│┤└┴├┼";

        int t = 0, r = 0, b = 0, l = 0;  // if top, right, bottom, left has bar.

        for (int y = N + 1; y >= 1; y--) {
            for (int x = 1; x <= N + 1; x++) {
                if (west[x][y] || east[x - 1][y]) t = 1; else t = 0;
                if (south[x][y] || north[x][y - 1]) r = 1; else r = 0;
                if (west[x][y - 1] || east[x - 1][y - 1]) b = 1; else b = 0;
                if (south[x - 1][y] || north[x - 1][y - 1]) l = 1; else l = 0;

                int index = (t << 3) + (r << 2) + (b << 1) + l;
                out += chars.charAt(index);
            }

            out += "\n";
        }

        return out;
    }

    public void walk(int startX, int startY, int endX, int endY) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.circle(startX + 0.5, startY + 0.5, 0.1);
        StdDraw.circle(endX + 0.5, endY + 0.5, 0.1);

        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                marked[x][y] = false;
            }
        }

        done = false;

        StdDraw.setPenRadius();

        explore(startX, startY, endX, endY);

        System.out.println("done!");
    }

    public void explore(int x, int y, int endX, int endY) {
        marked[x][y] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.show(100);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.1);
        StdDraw.show();

        if (x == endX && y == endY) {
            done = true;
            return;
        }

        while (!done) {
            if (!north[x][y] && !marked[x][y + 1]) {
                explore(x, y + 1, endX, endY);
            } else if (!south[x][y] && !marked[x][y - 1]) {
                explore(x, y - 1, endX, endY);
            } else if (!west[x][y] && !marked[x - 1][y]) {
                explore(x - 1, y, endX, endY);
            } else if (!east[x][y] && !marked[x + 1][y]) {
                explore(x + 1, y, endX, endY);
            } else {
                break;
            }
        }

        if (!done) {
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.show(100);
            StdDraw.filledCircle(x + 0.5, y + 0.5, 0.11);
            StdDraw.show();
        }
    }

    public void draw() {
        StdDraw.setXscale(0, N + 2);
        StdDraw.setYscale(0, N + 2);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                if (north[x][y]) StdDraw.line(x, y + 1, x + 1, y + 1);
                if (south[x][y]) StdDraw.line(x, y, x + 1, y);
                if (west[x][y]) StdDraw.line(x, y, x, y + 1);
                if (east[x][y]) StdDraw.line(x + 1, y, x + 1, y + 1);
            }
        }

    }

    public static void main(String[] args) {
        Maze m = new Maze(20);
        System.out.println(m);
        m.draw();

        m.walk(1, 1, 10, 10);
    }

}
