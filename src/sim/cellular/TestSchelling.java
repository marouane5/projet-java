package sim.cellular;

import java.awt.Color;
import java.util.Random;

import gui.GUISimulator;

public class TestSchelling {
    public static void main(String[] args) {
        int w = 50, h = 40, cs = 12, nColors = 3, K = 3;
        GUISimulator gui = new GUISimulator(w * cs, h * cs, Color.BLACK);
        Schelling model = new Schelling(w, h, nColors, K);
        Random rng = new Random(321);
        // fill with 10% empties and rest split across colors
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = rng.nextInt(10);
                if (r == 0) model.set(x, y, 0); // empty
                else model.set(x, y, 1 + rng.nextInt(nColors));
            }
        }
        model.snapshotInitial();
        Color[] palette = new Color[]{Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.MAGENTA};
        new CellularSimulator(gui, model, palette, cs);
    }
}

