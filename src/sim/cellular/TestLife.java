package sim.cellular;

import java.awt.Color;

import gui.GUISimulator;

public class TestLife {
    public static void main(String[] args) {
        int w = 50, h = 40, cs = 12;
        GUISimulator gui = new GUISimulator(w * cs, h * cs, Color.BLACK);
        Life life = new Life(w, h);
        // Glider at (1,1)
        life.set(1, 2, 1);
        life.set(2, 3, 1);
        life.set(3, 1, 1);
        life.set(3, 2, 1);
        life.set(3, 3, 1);
        life.snapshotInitial();
        Color[] palette = new Color[]{Color.BLACK, Color.WHITE};
        new CellularSimulator(gui, life, palette, cs);
    }
}

