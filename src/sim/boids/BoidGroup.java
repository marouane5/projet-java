package sim.boids;

import java.util.ArrayList;
import java.util.List;

/**
 * Parameters and update rules for a group of boids.
 */
public class BoidGroup {
    public final List<Boid> boids = new ArrayList<>();
    // Snapshot of initial state for restart()
    private final List<Boid> initial = new ArrayList<>();
    public double radius = 50.0;
    public double weightCohesion = 0.01;
    public double weightAlignment = 0.05;
    public double weightSeparation = 0.1;
    public double separationDistance = 20.0;
    public double maxSpeed = 4.0;
    public double dt = 1.0;

    public BoidGroup() {}

    /** Compute acceleration for one boid given neighbors within radius. */
    public Vec2 acceleration(Boid me, List<Boid> neighbors) {
        Vec2 a = new Vec2();
        if (neighbors.isEmpty()) return a;

        // cohesion: toward center
        Vec2 center = new Vec2();
        // alignment: toward average velocity
        Vec2 avgVel = new Vec2();
        // separation: away from too close
        Vec2 repel = new Vec2();

        int count = 0;
        for (Boid b : neighbors) {
            if (b == me) continue;
            double dx = b.pos.x - me.pos.x;
            double dy = b.pos.y - me.pos.y;
            double dist2 = dx*dx + dy*dy;
            double dist = Math.sqrt(dist2);
            if (dist <= radius && dist > 1e-9) {
                count++;
                center.x += b.pos.x; center.y += b.pos.y;
                avgVel.x += b.vel.x; avgVel.y += b.vel.y;
                if (dist < separationDistance) {
                    // repulsion proportional to overlap
                    repel.x -= dx / dist; repel.y -= dy / dist;
                }
            }
        }
        if (count > 0) {
            center.x /= count; center.y /= count;
            avgVel.x /= count; avgVel.y /= count;

            a.add(new Vec2(center.x - me.pos.x, center.y - me.pos.y).mul(weightCohesion));
            a.add(new Vec2(avgVel.x - me.vel.x, avgVel.y - me.vel.y).mul(weightAlignment));
            a.add(repel.mul(weightSeparation));
        }
        return a;
    }

    /** Save current boids as initial snapshot for restart(). */
    public void snapshotInitial() {
        initial.clear();
        for (Boid b : boids) {
            initial.add(new Boid(b.pos.x, b.pos.y, b.vel.x, b.vel.y));
        }
    }

    /** Restore boids to the initial snapshot (if any). */
    public void reInit() {
        if (initial.size() != boids.size()) {
            // best-effort: rebuild from snapshot if sizes differ
            boids.clear();
            for (Boid b : initial) {
                boids.add(new Boid(b.pos.x, b.pos.y, b.vel.x, b.vel.y));
            }
            return;
        }
        for (int i = 0; i < boids.size(); i++) {
            Boid src = initial.get(i);
            Boid dst = boids.get(i);
            dst.pos.x = src.pos.x; dst.pos.y = src.pos.y;
            dst.vel.x = src.vel.x; dst.vel.y = src.vel.y;
        }
    }
}
