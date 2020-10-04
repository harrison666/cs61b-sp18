package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Percolation {
    private Site[][] world;
    private int openNumber;
    private final int N;
    private WeightedQuickUnionUF dSet;
    private Set<Integer> top = new HashSet<>();
    private Set<Integer> bottom = new HashSet<>();
    private boolean percolated = false;

    private class Site {
        private int x;
        private int y;
        private boolean isOpen;

        public Site(int x, int y) {
            this.x = x;
            this.y = y;
            isOpen = false;
        }
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) throws IllegalArgumentException {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be larger than 0 but got " + N);
        }
        world = new Site[N][N];
        openNumber = 0;
        this.N = N;
        for (int i = 0; i < N; i++) {
            for (int j  = 0; j < N; j++) {
                world[i][j] = new Site(i, j);
            }
        }
        dSet = new WeightedQuickUnionUF(N * N);
    }

    // transfer the 2D location to 1D integer
    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        if (world[row][col].isOpen) {
            return;
        }
        world[row][col].isOpen = true;
        openNumber++;
        unionNeighbors(row, col);
        if (row == 0) {
            top.add(xyTo1D(row, col));
        }
        if (row == N - 1) {
            bottom.add(xyTo1D(row, col));
        }

        if (top.contains(xyTo1D(row, col)) && bottom.contains(xyTo1D(row, col))) {
            percolated = true;
        }
    }

    private ArrayList<Site> findNeighbors(int row, int col) {
        ArrayList<Site> neighbors = new ArrayList<>();
        if (row != 0) {
            neighbors.add(world[row - 1][col]);
        }
        if (row != N - 1) {
            neighbors.add(world[row + 1][col]);
        }
        if (col != 0) {
            neighbors.add(world[row][col - 1]);
        }
        if (col != N - 1) {
            neighbors.add(world[row][col + 1]);
        }
        return neighbors;
    }

    // union the open neighbors of the site
    private void unionNeighbors(int row, int col) {
        ArrayList<Site> neighbors = findNeighbors(row, col);
        for (Site neighbor : neighbors) {
            if (isOpen(neighbor.x, neighbor.y)) {
                dSet.union(xyTo1D(neighbor.x, neighbor.y), xyTo1D(row, col));
                if (top.contains(xyTo1D(neighbor.x, neighbor.y))) {
                    top.add(xyTo1D(row, col));
                }
                if (bottom.contains(xyTo1D(neighbor.x, neighbor.y))) {
                    bottom.add(xyTo1D(row, col));
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return world[row][col].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
        return top.contains(xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation myWorld = new Percolation(5);
        myWorld.open(0, 1);
        myWorld.open(1, 1);
        myWorld.open(2, 1);
        myWorld.open(2, 2);
        myWorld.open(3, 2);
        myWorld.open(4, 2);
        myWorld.open(2, 4);
        myWorld.open(3, 4);


        Assert.assertTrue(myWorld.percolates());
        Assert.assertEquals(9, myWorld.numberOfOpenSites());
        Assert.assertFalse(myWorld.isFull(2, 4));
    }
}
