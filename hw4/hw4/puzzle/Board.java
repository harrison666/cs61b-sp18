package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import javax.xml.transform.Source;

public class Board implements WorldState{
    private int[][] tiles;
    private final int N;
    private final int BLANK = 0;

    public Board(int[][] tiles){
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > N - 1 || j < 0 || j > N - 1) { // Corner case check
            throw new IndexOutOfBoundsException("Invalid index given: i == " + i + " j == " + j);
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     */

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int dist = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int cur = tileAt(i, j);
                if (cur != i * size() + j + 1 && cur != BLANK) {
                    dist++;
                }
            }
        }
        return dist;
    }
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int cur = tileAt(i, j);
                if (cur != i * size() + j + 1 && cur != BLANK) {
                    int row = (cur - 1) / size();
                    int col = (cur - 1) % size();
                    dist += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return dist;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board board1 = (Board) y;

        return tiles != null ? tiles.equals(board1.tiles) : board1.tiles == null;
    }



    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
