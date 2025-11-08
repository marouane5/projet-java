package sim.boids;

/** Simple 2D vector utility. */
public class Vec2 {
    public double x, y;

    public Vec2() { this(0, 0); }
    public Vec2(double x, double y) { this.x = x; this.y = y; }

    public Vec2 set(double x, double y) { this.x = x; this.y = y; return this; }
    public Vec2 copy() { return new Vec2(x, y); }
    public Vec2 add(Vec2 o) { this.x += o.x; this.y += o.y; return this; }
    public Vec2 sub(Vec2 o) { this.x -= o.x; this.y -= o.y; return this; }
    public Vec2 mul(double k) { this.x *= k; this.y *= k; return this; }
    public double dot(Vec2 o) { return x * o.x + y * o.y; }
    public double norm() { return Math.hypot(x, y); }
    public Vec2 normalize() { double n = norm(); if (n > 1e-12) { x /= n; y /= n; } return this; }
    public Vec2 clip(double max) { double n = norm(); if (n > max && n > 1e-12) { mul(max / n); } return this; }
}

