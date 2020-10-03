package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.TileEngine.TERenderer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class WorldGenerator {

    /// Static members
    private static final int MAXROOMWIDTH = 6;
    private static final int MAXROOMHEIGHT = 6;





    /// Instance members
    private int width;
    private int height;
    private Position initialPosition;
    private Random random;
    private TETile[][] world;



    /// Constructors


    /**
     * Returns WorldGenerator object without random seed specified
     *
     * @param w        width of generated world
     * @param h        height of generated world
     * @param initialX x coordinate of initial LOCKED_DOOR
     * @param initialY y coordinate of initial LOCKED_DOOR
     */
    WorldGenerator(int w, int h, int initialX, int initialY) {
        width = w;
        height = h;
        initialPosition = new Position(initialX, initialY);
        random = new Random();
    }


    /**
     * Returns WorldGenerator object with random seed specified
     *
     * @param w        width of generated world
     * @param h        height of generated world
     * @param initialX x coordinate of initial LOCKED_DOOR
     * @param initialY y coordinate of initial LOCKED_DOOR
     * @param seed     random seed used to generate world
     */
    WorldGenerator(int w, int h, int initialX, int initialY, long seed) {
        width = w;
        height = h;
        initialPosition = new Position(initialX, initialY);
        random = new Random(seed);
    }

    /// Nested class for easy access to x, y coordinates
    private class Position {
        int x;
        int y;

        Position(int xx, int yy) {
            x = xx;
            y = yy;
        }
    }

    /// Private methods
    /* Initialize a world filling everything with NOTHING*/
    private void initialize() {
        world = new TETile[width][height];
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                world[w][h] = Tileset.NOTHING;
            }
        }
    }

    private boolean checkAvailability(Position leftBottom, Position rightUpper) {
        if (leftBottom.x < 0 || width <= leftBottom.x
            || leftBottom.y < 0 || height <= leftBottom.y
            || rightUpper.x <= 0 || width <= rightUpper.x
            || rightUpper.y <= 0 || height <= rightUpper.y) {
            return false;
        }

        for (int x = leftBottom.x; x <= rightUpper.x; x++) {
            for (int y = leftBottom.y; y <= rightUpper.y; y++) {
                TETile currentTile = world[x][y];
                if (currentTile != Tileset.NOTHING) {
                    return false;
                }
            }
        }

        return true;
    }

    /* Returns random positions for a new room on the north of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionNorth(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int leftBottomX = entryPositionX - random.nextInt(w) - 1;
        int leftBottomY = entryPositionY;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!checkAvailability(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the east of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionEast(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int leftBottomX = entryPositionX;
        int leftBottomY = entryPositionY - random.nextInt(h) - 1;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!checkAvailability(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the south of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionSouth(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int rightUpperX = entryPositionX + random.nextInt(w) + 1;
        int rightUpperY = entryPositionY;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!checkAvailability(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the west of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionWest(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int rightUpperX = entryPositionX;
        int rightUpperY = entryPositionY + random.nextInt(h) + 1;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!checkAvailability(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }
    /* Makes rectangular room filled with FLOOR and surrounded by WALL */
    private void makeRoom(Position leftBottom, Position rightUpper) {
        for (int x = leftBottom.x; x <= rightUpper.x; x++) {
            for (int y = leftBottom.y; y <= rightUpper.y; y++) {
                TETile currentTile = world[x][y];
                if (x == leftBottom.x
                        || x == rightUpper.x
                        || y == leftBottom.y
                        || y == rightUpper.y) {
                    world[x][y] = Tileset.WALL;
                } else {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private void makeInitialEntrance(Position initialEntryPosition) {
        world[initialEntryPosition.x][initialEntryPosition.y] = Tileset.LOCKED_DOOR;
    }

    private void makeEntrance(Position EntryPosition) {
        world[EntryPosition.x][EntryPosition.y] = Tileset.FLOOR;
    }

    private void makeExit(Position exitPosition) {
        world[exitPosition.x][exitPosition.y] = Tileset.FLOOR;
    }

    private String getReverseDirection(String direction) {
        switch (direction) {
            case "N":
                return "S";
            case "E":
                return "W";
            case "S":
                return "N";
            default:
                return "E";
        }
    }

    private Object[] randomExit(Position leftBottom, Position rightUpper, String currentDirection) {
        int w = rightUpper.x - leftBottom.x - 1;
        int h = rightUpper.y - leftBottom.y - 1;
        Object[] exitAndDirection = new Object[2];

        // Decide nextDirection
        List<String> directions = new LinkedList<>();
        directions.add("N");
        directions.add("E");
        directions.add("S");
        directions.add("W");
        directions.remove(getReverseDirection(currentDirection));
        String nextDirection = directions.get(random.nextInt(directions.size()));

        // Decide next exitPosition
        Position nextExitPosition;
        switch (nextDirection) {
            case "N":
                nextExitPosition = new Position(rightUpper.x - random.nextInt(w) - 1, rightUpper.y);
                break;
            case "E":
                nextExitPosition = new Position(rightUpper.x, rightUpper.y - random.nextInt(h) - 1);
                break;
            case "S":
                nextExitPosition = new Position(leftBottom.x + random.nextInt(w) + 1, leftBottom.y);
                break;
            default:
                nextExitPosition = new Position(leftBottom.x, leftBottom.y + random.nextInt(h) + 1);
                break;
        }

        exitAndDirection[0] = nextExitPosition;
        exitAndDirection[1] = nextDirection;
        return exitAndDirection;
    }

    private void recursiveAddRandomRoom(Position exitPosition, String currentDirection) {
        int w = random.nextInt(MAXROOMHEIGHT) + 1;
        int h = random.nextInt(MAXROOMHEIGHT) + 1;

        Position entryPosition;
        Position[] lrPositions;
        switch (currentDirection) {
            case "N":
                entryPosition = new Position(exitPosition.x, exitPosition.y + 1);
                lrPositions = randomPositionNorth(w, h, entryPosition);
                break;
            case "E":
                entryPosition = new Position(exitPosition.x + 1, exitPosition.y);
                lrPositions = randomPositionEast(w, h, entryPosition);
                break;
            case "S":
                entryPosition = new Position(exitPosition.x, exitPosition.y - 1);
                lrPositions = randomPositionSouth(w, h, entryPosition);
                break;
            default:
                entryPosition = new Position(exitPosition.x - 1, exitPosition.y);
                lrPositions = randomPositionWest(w, h, entryPosition);
                break;
        }

        if (lrPositions != null) {
            makeExit(exitPosition);
            Position leftBottom = lrPositions[0];
            Position rightUpper = lrPositions[1];
            makeRoom(leftBottom, rightUpper);
            makeEntrance(entryPosition);

            switch (random.nextInt(3) + 1) {
                /* Comment in below for more simpler world */
//                case 1: monoExit(leftBottom, rightUpper, currentDirection); break;
                case 2:
                    biExit(leftBottom, rightUpper, currentDirection);
                    break;
                default:
                    triExit(leftBottom, rightUpper, currentDirection);
                    break;
            }
        }

    }

    /* Makes a mono exit from a given rectangular room and calls another recursiveAddRandomRoom */
    private void monoExit(Position leftBottom, Position rightUpper, String currentDirection) {
        Object[] exitAndDirection = randomExit(leftBottom, rightUpper, currentDirection);
        Position nextExitPosition = (Position) exitAndDirection[0];
        String nextDirection = (String) exitAndDirection[1];
        recursiveAddRandomRoom(nextExitPosition, nextDirection);
    }

    /* Makes bi exits from a given rectangular room and calls another recursiveAddRandomRoom */
    private void biExit(Position leftBottom, Position rightUpper, String currentDirection) {
        Object[] exitAndDirection1 = randomExit(leftBottom, rightUpper, currentDirection);
        Position nextExitPosition1 = (Position) exitAndDirection1[0];
        String nextDirection1 = (String) exitAndDirection1[1];

        Object[] exitAndDirection2;
        Position nextExitPosition2 = nextExitPosition1;
        String nextDirection2 = nextDirection1;
        while (nextDirection2.equals(nextDirection1)) {
            exitAndDirection2 = randomExit(leftBottom, rightUpper, currentDirection);
            nextExitPosition2 = (Position) exitAndDirection2[0];
            nextDirection2 = (String) exitAndDirection2[1];
        }

        recursiveAddRandomRoom(nextExitPosition1, nextDirection1);
        recursiveAddRandomRoom(nextExitPosition2, nextDirection2);
    }

    /* Makes tri exits from a given rectangular room and calls another recursiveAddRandomRoom */
    private void triExit(Position leftBottom, Position rightUpper, String currentDirection) {
        Object[] exitAndDirection1 = randomExit(leftBottom, rightUpper, currentDirection);
        Position nextExitPosition1 = (Position) exitAndDirection1[0];
        String nextDirection1 = (String) exitAndDirection1[1];

        Object[] exitAndDirection2;
        Position nextExitPosition2 = nextExitPosition1;
        String nextDirection2 = nextDirection1;
        while (nextDirection2.equals(nextDirection1)) {
            exitAndDirection2 = randomExit(leftBottom, rightUpper, currentDirection);
            nextExitPosition2 = (Position) exitAndDirection2[0];
            nextDirection2 = (String) exitAndDirection2[1];
        }

        Object[] exitAndDirection3;
        Position nextExitPosition3 = nextExitPosition1;
        String nextDirection3 = nextDirection1;
        while (nextDirection3.equals(nextDirection1) || nextDirection3.equals(nextDirection2)) {
            exitAndDirection3 = randomExit(leftBottom, rightUpper, currentDirection);
            nextExitPosition3 = (Position) exitAndDirection3[0];
            nextDirection3 = (String) exitAndDirection3[1];
        }

        recursiveAddRandomRoom(nextExitPosition1, nextDirection1);
        recursiveAddRandomRoom(nextExitPosition2, nextDirection2);
        recursiveAddRandomRoom(nextExitPosition3, nextDirection3);
    }




    /// Package-private methods
    /**
     * Returns a world generated with given random seed
     *
     * @return a randomly generated world
     */
    TETile[][] generate(){
        initialize();

        // Make the first room
        Position[] lrPosition = randomPositionNorth(MAXROOMWIDTH, MAXROOMHEIGHT, initialPosition);
        makeRoom(lrPosition[0], lrPosition[1]);
        makeInitialEntrance(initialPosition);

        // Recursively call recursiveAddRandomRoom via first calling triExit
        triExit(lrPosition[0], lrPosition[1], "N");

        return world;
    }


    // Main method just to check this class works itself
    public static void main(String[] args) {
        int w = 80;
        int h = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(w, h);
        WorldGenerator wg = new WorldGenerator(w, h, 40, 5, 42);
        wg.initialize();
        wg.generate();
        ter.renderFrame(wg.world);
    }
}