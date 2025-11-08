package sim.cellular;

/** Conway's Game of Life. States: 0=dead, 1=alive. */
public class Life extends CellularAutomaton {
    public Life(int width, int height) {
        super(width, height);
    }

    @Override
    protected int computeNext(int x, int y) {
        int alive = neighbors8Count(x, y, 1);
        int current = get(x, y);
        if (current == 1) {
            return (alive == 2 || alive == 3) ? 1 : 0;
        } else {
            return (alive == 3) ? 1 : 0;
        }
    }
}

