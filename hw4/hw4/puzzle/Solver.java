package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.*;

public class Solver {

    int finalMove;
    LinkedList<WorldState> moveSeq = new LinkedList<>();

    private class Node implements Comparable<Node>{
        private WorldState ws;
        private int numMove;
        private Node preNode;

        public Node(WorldState ws, int numMove, Node preNode) {
            this.ws = ws;
            this.numMove = numMove;
            this.preNode = preNode;
        }

        @Override
        public int compareTo(Node node) {
            return Integer.compare(numMove + ws.estimatedDistanceToGoal(), node.numMove + node.ws.estimatedDistanceToGoal());
        }
    }


    /**
     * Constructor which solves the puzzle, computing everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the puzzle using the A* algorithm. Assumes a solution exists.
     * @param initial
     */
    public Solver(WorldState initial){
        MinPQ<Node> pq = new MinPQ<Node>();
        Node initialNode = new Node(initial, 0, null);
        pq.insert(initialNode);

        Node X = pq.delMin();
        while (!X.ws.isGoal()) {
            for (WorldState w : X.ws.neighbors()) {
                if (X.preNode == null || !w.equals(X.preNode.ws)) {
                    Node newNode = new Node(w, X.numMove + 1, X);
                    pq.insert(newNode);
                }
            }
            X = pq.delMin();
        }
        finalMove = X.numMove;

        while (X != null) {
            moveSeq.addFirst(X.ws);
            X = X.preNode;
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.
     */
    public int moves() {
        return finalMove;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState to the solution.
     * @return
     */
    public Iterable<WorldState> solution() {
        return moveSeq;
    }
}