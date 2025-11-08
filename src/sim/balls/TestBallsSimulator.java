package sim.balls;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import gui.GUISimulator;

/**
 * Demo for the Balls simulator.
 */
public class TestBallsSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        List<Point> pos = Arrays.asList(new Point(100, 100), new Point(200, 150), new Point(300, 250));
        List<Point> vel = Arrays.asList(new Point(4, 3), new Point(-3, 5), new Point(2, -4));
        Balls balls = new Balls(pos, vel);

        new BallsSimulator(gui, balls,20, Color.ORANGE);
    }
}

