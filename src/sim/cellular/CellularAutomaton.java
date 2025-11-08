package sim.cellular;

/**
 * Base class for toroidal-grid cellular automata with integer states.
 * Maintains a state grid and a buffer grid to compute the next step without
 * in-place modifications.
 */
public abstract class CellularAutomaton {
    protected final int width;
    protected final int height;
    protected final int[][] state;
    protected final int[][] buffer;
    protected final int[][] initial;

    public CellularAutomaton(int width, int height) {
        this.width = width;
        this.height = height;
        this.state = new int[height][width];
        this.buffer = new int[height][width];
        this.initial = new int[height][width];
    }

    /** Copy current state as initial snapshot for restart(). */
    public void snapshotInitial() {
        for (int y = 0; y < height; y++) {
            System.arraycopy(state[y], 0, initial[y], 0, width);
        }
    }

    /** Restore the initial snapshot. */
    public void reInit() {
        for (int y = 0; y < height; y++) {
            System.arraycopy(initial[y], 0, state[y], 0, width);
        }
    }

    public int get(int x, int y) {
        int xx = wrapX(x), yy = wrapY(y);
        return state[yy][xx];
    }

    public void set(int x, int y, int val) {
        state[wrapY(y)][wrapX(x)] = val;
    }

    protected int wrapX(int x) {
        int r = x % width;
        return r < 0 ? r + width : r;
    }

    protected int wrapY(int y) {
        int r = y % height;
        return r < 0 ? r + height : r;
    }

    /** Count 8-neighbors equal to given value on torus. */
    public int neighbors8Count(int x, int y, int value) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                if (get(x + dx, y + dy) == value) count++;
            }
        }
        return count;
    }

    /** Compute next-state value for cell (x,y). */
    protected abstract int computeNext(int x, int y);

    /** Perform one step: compute into buffer then swap. */
    public void step() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buffer[y][x] = computeNext(x, y);
            }
        }
        // swap: copy buffer to state
        for (int y = 0; y < height; y++) {
            System.arraycopy(buffer[y], 0, state[y], 0, width);
        }
    }
}

