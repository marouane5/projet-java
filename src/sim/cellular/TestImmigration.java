package sim.cellular;

import java.awt.Color;
import java.util.Random;

import gui.GUISimulator;

public class TestImmigration {
    public static void main(String[] args) {
        int w = 60, h = 45, cs = 10, n = 5;
        GUISimulator gui = new GUISimulator(w * cs, h * cs, Color.BLACK);
        Immigration game = new Immigration(w, h, n);
        Random rng = new Random(123);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                game.set(x, y, rng.nextInt(n));
            }
        }
        game.snapshotInitial();
        Color[] palette = new Color[]{Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
        new CellularSimulator(gui, game, palette, cs);
    }
}

