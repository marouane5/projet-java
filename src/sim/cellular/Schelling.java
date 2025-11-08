package sim.cellular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Schelling segregation model on a toroidal grid.
 * States: 0=empty, 1..nColors=families.
 * If a family's cell has strictly more than K neighbors of different color,
 * it moves to an empty home. Moves are executed using an available homes list
 * without conflicts (one family per home).
 */
public class Schelling extends CellularAutomaton {
    private final int nColors;
    private final int K;
    private final Random rng = new Random(42);

    public Schelling(int width, int height, int nColors, int K) {
        super(width, height);
        if (nColors < 1) throw new IllegalArgumentException("nColors >= 1");
        this.nColors = nColors;
        this.K = K;
    }

    private boolean isUnhappy(int x, int y) {
        int me = get(x, y);
        if (me == 0) return false; // empty
        int diff = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int o = get(x + dx, y + dy);
                if (o != 0 && o != me) diff++;
            }
        }
        return diff > K;
    }

    @Override
    protected int computeNext(int x, int y) {
        // not used: step() overridden to perform global relocation
        return get(x, y);
    }

    /**
     * Perform one relocation step: compute unhappy agents, move them to empty
     * homes at random without conflicts, others stay.
     */
    @Override
    public void step() {
        // list empty homes
        List<int[]> empties = new ArrayList<>();
        for (int yy = 0; yy < height; yy++) {
            for (int xx = 0; xx < width; xx++) {
                if (state[yy][xx] == 0) empties.add(new int[]{xx, yy});
            }
        }
        Collections.shuffle(empties, rng);

        // list unhappy agents
        List<int[]> movers = new ArrayList<>();
        for (int yy = 0; yy < height; yy++) {
            for (int xx = 0; xx < width; xx++) {
                if (state[yy][xx] != 0 && isUnhappy(xx, yy)) {
                    movers.add(new int[]{xx, yy});
                }
            }
        }

        // start from current state as base
        for (int y = 0; y < height; y++) {
            System.arraycopy(state[y], 0, buffer[y], 0, width);
        }

        // move each unhappy agent to a free home if available
        int e = 0;
        for (int[] m : movers) {
            if (e >= empties.size()) break; // no more free homes this step
            int mx = m[0], my = m[1];
            int color = state[my][mx];
            int[] home = empties.get(e++);
            // vacate old location
            buffer[my][mx] = 0;
            // occupy new location
            buffer[home[1]][home[0]] = color;
        }

        // swap buffer to state
        for (int y = 0; y < height; y++) {
            System.arraycopy(buffer[y], 0, state[y], 0, width);
        }
    }
}

