package sim.boids;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;
import sim.core.Event;
import sim.core.EventManager;

/** Simulator for multiple boid groups. */
public class BoidsSimulator implements Simulable {
    private final GUISimulator gui;
    private final List<BoidGroup> groups = new ArrayList<>();
    private final List<Color> colors = new ArrayList<>();
    private final EventManager manager = new EventManager();
    private final int width, height;
    private final Random rng = new Random(7);

    public BoidsSimulator(GUISimulator gui) {
        this.gui = gui;
        this.width = gui.getWidth();
        this.height = gui.getHeight();
        gui.setSimulable(this);
        scheduleStep(1);
    }

    public void addGroup(BoidGroup g, Color color) {
        // Take a snapshot so that restart() can restore initial positions
        g.snapshotInitial();
        groups.add(g); colors.add(color);
    }

    private void scheduleStep(long date) {
        manager.addEvent(new Event(date) {
            @Override public void execute() { advance(); draw(); scheduleStep(getDate()+1); }
        });
    }

    private void advance() {
        // compute accelerations first
        List<Vec2[]> acc = new ArrayList<>();
        for (BoidGroup g : groups) {
            Vec2[] a = new Vec2[g.boids.size()];
            for (int i = 0; i < g.boids.size(); i++) {
                Boid me = g.boids.get(i);
                // neighbors: all boids in same group for now
                a[i] = g.acceleration(me, g.boids);
            }
            acc.add(a);
        }
        // integrate
        for (int gi = 0; gi < groups.size(); gi++) {
            BoidGroup g = groups.get(gi);
            Vec2[] a = acc.get(gi);
            for (int i = 0; i < g.boids.size(); i++) {
                Boid b = g.boids.get(i);
                b.vel.add(a[i].mul(g.dt));
                // clip speed
                double n = b.vel.norm();
                if (n > g.maxSpeed) b.vel.mul(g.maxSpeed / n);
                b.pos.add(b.vel.copy().mul(g.dt));
                // wrap around borders for now
                if (b.pos.x < 0) b.pos.x += width;
                if (b.pos.y < 0) b.pos.y += height;
                if (b.pos.x >= width) b.pos.x -= width;
                if (b.pos.y >= height) b.pos.y -= height;
            }
        }
    }

    private void draw() {
        gui.reset();
        for (int gi = 0; gi < groups.size(); gi++) {
            BoidGroup g = groups.get(gi);
            Color c = colors.get(gi);
            for (Boid b : g.boids) {
                gui.addGraphicalElement(new Oval((int)b.pos.x, (int)b.pos.y, c, c, 4));
            }
        }
    }

    @Override public void next() { manager.next(); }
    @Override public void restart() {
        for (BoidGroup g : groups) g.reInit();
        manager.restart();
        draw();
    }
}
