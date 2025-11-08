package sim.boids;

import java.awt.Color;
import java.util.Random;

import gui.GUISimulator;

public class TestBoids {
    public static void main(String[] args) {
        int W = 900, H = 600;
        GUISimulator gui = new GUISimulator(W, H, Color.BLACK);

        BoidsSimulator sim = new BoidsSimulator(gui);

        BoidGroup g1 = new BoidGroup();
        g1.radius = 60; g1.weightCohesion = 0.01; g1.weightAlignment = 0.08; g1.weightSeparation = 0.15;
        g1.separationDistance = 18; g1.maxSpeed = 5; g1.dt = 1;

        BoidGroup g2 = new BoidGroup();
        g2.radius = 80; g2.weightCohesion = 0.02; g2.weightAlignment = 0.05; g2.weightSeparation = 0.08;
        g2.separationDistance = 22; g2.maxSpeed = 4; g2.dt = 1;

        Random rng = new Random(42);
        for (int i = 0; i < 60; i++) {
            g1.boids.add(new Boid(rng.nextInt(W/2), rng.nextInt(H), rng.nextDouble()*2-1, rng.nextDouble()*2-1));
        }
        for (int i = 0; i < 60; i++) {
            g2.boids.add(new Boid(W/2 + rng.nextInt(W/2), rng.nextInt(H), rng.nextDouble()*2-1, rng.nextDouble()*2-1));
        }

        sim.addGroup(g1, Color.ORANGE);
        sim.addGroup(g2, Color.CYAN);
    }
}

