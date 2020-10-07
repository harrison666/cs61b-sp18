package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound = false;
    private Maze maze;
    private int[] cameFrom;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        cameFrom = new int[maze.V()];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        /* Set point where circle search starts */
        cc(0);
        announce();
    }

    // Helper methods go here
    private void cc(int v) {
        marked[v] = true;

        for (int w : maze.adj(v)) {
            if (cycleFound) {
                return;
            }

            if (!marked[w]) {
                cameFrom[w] = v;
                cc(w);
            } else if (w != cameFrom[v]) {
                cameFrom[w] = v;

                int cur = v;
                edgeTo[cur] = cameFrom[cur];
                while (cur != w) {
                    cur = cameFrom[cur];
                    edgeTo[cur] = cameFrom[cur];
                }

                cycleFound = true;
                return;
            }
        }
    }

}

