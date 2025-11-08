package sim.cellular;

import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import sim.core.Event;
import sim.core.EventManager;

/**
 * Simulator for cellular automata, draws the grid as colored rectangles.
 * Uses an EventManager to step the automaton over time.
 */
public class CellularSimulator implements Simulable {
    private final GUISimulator gui;
    private final CellularAutomaton automaton;
    private final EventManager manager = new EventManager();
    private final Color[] palette;
    private final int cellSize;

    public CellularSimulator(GUISimulator gui, CellularAutomaton automaton, Color[] palette, int cellSize) {
        this.gui = gui;
        this.automaton = automaton;
        this.palette = palette.clone();
        this.cellSize = cellSize;
        gui.setSimulable(this);
        scheduleStep(1);
        draw();
    }

    private void scheduleStep(long date) {
        manager.addEvent(new Event(date) {
            @Override
            public void execute() {
                automaton.step();
                draw();
                scheduleStep(getDate() + 1);
            }
        });
    }

    private void draw() {
        gui.reset();
        int h = automaton.height;
        int w = automaton.width;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int val = automaton.state[y][x];
                Color c = palette[val % palette.length];
                int cx = x * cellSize + cellSize / 2;
                int cy = y * cellSize + cellSize / 2;
                gui.addGraphicalElement(new Rectangle(cx, cy, c, c, cellSize));
            }
        }
    }

    @Override
    public void next() {
        manager.next();
    }

    @Override
    public void restart() {
        automaton.reInit();
        manager.restart();
        draw();
    }
}

