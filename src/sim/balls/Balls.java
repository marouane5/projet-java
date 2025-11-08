package sim.balls;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple collection of moving balls, each with a position and a velocity.
 * Provides translation and reinitialization facilities.
 */
public class Balls {
    private final List<Point> initialPositions = new ArrayList<>();
    private final List<Point> positions = new ArrayList<>();
    private final List<Point> velocities = new ArrayList<>(); // vx, vy in pixels per step

    /** Create from arrays of positions and velocities. */
    public Balls(List<Point> pos, List<Point> vel) {
        if (pos.size() != vel.size()) {
            throw new IllegalArgumentException("positions and velocities must have same size");
        }
        for (int i = 0; i < pos.size(); i++) {
            Point p = new Point(pos.get(i));
            Point v = new Point(vel.get(i));
            positions.add(new Point(p));
            velocities.add(new Point(v));
            initialPositions.add(new Point(p));
        }
    }

    public int size() { return positions.size(); }

    public Point getPosition(int i) { return new Point(positions.get(i)); }

    public Point getVelocity(int i) { return new Point(velocities.get(i)); }

    /** Translate all balls by (dx, dy). */
    public void translate(int dx, int dy) {
        for (Point p : positions) {
            p.translate(dx, dy);
        }
    }

    /** Advance by one step according to stored velocities. */
    public void step() {
        for (int i = 0; i < positions.size(); i++) {
            Point p = positions.get(i);
            Point v = velocities.get(i);
            p.translate(v.x, v.y);
        }
    }

    /** Reverse velocity component i (helper for bouncing). */
    public void invertVx(int i) { velocities.get(i).x = -velocities.get(i).x; }

    public void invertVy(int i) { velocities.get(i).y = -velocities.get(i).y; }

    /** Reset to initial positions, keep velocities unchanged. */
    public void reInit() {
        for (int i = 0; i < positions.size(); i++) {
            Point p0 = initialPositions.get(i);
            Point p = positions.get(i);
            p.x = p0.x;
            p.y = p0.y;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Balls[");
        for (int i = 0; i < positions.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(positions.get(i).x).append(',').append(positions.get(i).y);
        }
        sb.append(']');
        return sb.toString();
    }
}

