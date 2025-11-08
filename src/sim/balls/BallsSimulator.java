package sim.balls;

import java.awt.Color;
import java.awt.Point;

import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;
import sim.core.Event;
import sim.core.EventManager;

/**
 * Simulator for moving balls. Integrates with GUISimulator and uses an
 * EventManager to drive animation at regular steps.
 */
public class BallsSimulator implements Simulable {
    private final GUISimulator gui;
    private final Balls balls;
    private final EventManager manager = new EventManager();
    private final int radius;
    private final Color color;
    private final int width;
    private final int height;

    /**
     * @param gui GUI to draw in
     * @param balls model
     * @param radius ball radius in pixels
     * @param color drawing color
     */
    public BallsSimulator(GUISimulator gui, Balls balls, int radius, Color color) {
        this.gui = gui;
        this.balls = balls;
        this.radius = radius;
        this.color = color;
        this.width = gui.getWidth();
        this.height = gui.getHeight();
        gui.setSimulable(this);
        scheduleStep(1);
        draw();
    }

    private void scheduleStep(long date) {
        manager.addEvent(new Event(date) {
            @Override
            public void execute() {
                advanceOneStep();
                // reschedule next frame
                scheduleStep(getDate() + 1);
            }
        });
    }

    private void advanceOneStep() {
        balls.step();
        // bounce handling on borders
        for (int i = 0; i < balls.size(); i++) {
            Point p = balls.getPosition(i);
            Point v = balls.getVelocity(i);
            int r = radius;
            if (p.x - r < 0 && v.x < 0) balls.invertVx(i);
            if (p.x + r > width && v.x > 0) balls.invertVx(i);
            if (p.y - r < 0 && v.y < 0) balls.invertVy(i);
            if (p.y + r > height && v.y > 0) balls.invertVy(i);
        }
        draw();
    }

    private void draw() {
        gui.reset();
        for (int i = 0; i < balls.size(); i++) {
            Point p = balls.getPosition(i);
            gui.addGraphicalElement(new Oval(p.x, p.y, color, color, radius));
        }
    }

    @Override
    public void next() {
        manager.next();
    }

    @Override
    public void restart() {
        balls.reInit();
        manager.restart();
        draw();
    }
}

