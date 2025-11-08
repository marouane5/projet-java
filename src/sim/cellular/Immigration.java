package sim.cellular;

/**
 * Immigration game with n states: a cell in state k goes to k+1 (mod n)
 * if it has at least 3 neighbors in state k+1.
 */
public class Immigration extends CellularAutomaton {
    private final int nStates;

    public Immigration(int width, int height, int nStates) {
        super(width, height);
        if (nStates < 2) throw new IllegalArgumentException("nStates >= 2");
        this.nStates = nStates;
    }

    @Override
    protected int computeNext(int x, int y) {
        int k = get(x, y);
        int kp1 = (k + 1) % nStates;
        int count = neighbors8Count(x, y, kp1);
        if (count >= 3) return kp1;
        return k;
    }
}

