package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static int hexRowWidth(int s, int i) {
        if (i < s) {
            return s + 2 * i;
        }
        return s + 2 * (s - 1) - 2 * (i - s);
    }

    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi++) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = t;
        }
    }


    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int yi = 0; yi < 2 * s; yi++) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, yi);

            addRow(world, rowStartP, rowWidth, t);
        }
    }

    //according to the Position of bottom hexagon, calculate the positions of all other positions.
    public static Position[] calPosition(Position p, int s) {
        Position[] allPos = new Position[19];
        int k = 0;
        for (int i = -2; i <= 2; i++) {
            for (int j = 0; j < 5 - Math.abs(i); j++){
                allPos[k] = new Position(p.x + i * (2 * s - 1), p.y + s * Math.abs(i) + j * 2 * s);
                k++;
            }
        }
        return allPos;
    }

    public static void tesselate(TETile[][] world, Position p, int s) {
        Position[] allPos = calPosition(p, s);
        for (Position pos : allPos) {
            addHexagon(world, pos, s, randomTile());
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(7);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.SAND;
            case 5: return Tileset.MOUNTAIN;
            case 6: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {

        int WIDTH = 30; int HEIGHT = 30;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexWorld[x][y] = Tileset.NOTHING;
            }
        }

        Position pos0 = new Position(13,0);
        tesselate(hexWorld, pos0, 3);

        ter.renderFrame(hexWorld);
    }

    @Test
    public void testHexRowWidth() {
        assertEquals(3, hexRowWidth(3, 5));
        assertEquals(5, hexRowWidth(3, 4));
        assertEquals(7, hexRowWidth(3, 3));
        assertEquals(7, hexRowWidth(3, 2));
        assertEquals(5, hexRowWidth(3, 1));
        assertEquals(3, hexRowWidth(3, 0));
        assertEquals(2, hexRowWidth(2, 0));
        assertEquals(4, hexRowWidth(2, 1));
        assertEquals(4, hexRowWidth(2, 2));
        assertEquals(2, hexRowWidth(2, 3));
    }

    @Test
    public void testHexRowOffset() {
        assertEquals(0, hexRowOffset(3, 5));
        assertEquals(-1, hexRowOffset(3, 4));
        assertEquals(-2, hexRowOffset(3, 3));
        assertEquals(-2, hexRowOffset(3, 2));
        assertEquals(-1, hexRowOffset(3, 1));
        assertEquals(0, hexRowOffset(3, 0));
        assertEquals(0, hexRowOffset(2, 0));
        assertEquals(-1, hexRowOffset(2, 1));
        assertEquals(-1, hexRowOffset(2, 2));
        assertEquals(0, hexRowOffset(2, 3));
    }

}
