package sim.boids;

/** Boid with position and velocity. */
public class Boid {
    public final Vec2 pos = new Vec2();
    public final Vec2 vel = new Vec2();

    public Boid(double x, double y, double vx, double vy) {
        this.pos.set(x, y);
        this.vel.set(vx, vy);
    }
}

