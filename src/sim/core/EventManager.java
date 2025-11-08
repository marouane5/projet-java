package sim.core;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Discrete event manager. Maintains a current date and a priority queue of
 * events. On each {@link #next()}, the date is incremented and all events
 * scheduled for this date are executed in increasing date order.
 * <p>
 * Initial events added before the first call to {@link #next()} are recorded
 * to allow {@link #restart()} to rebuild the initial schedule/state.
 */
public class EventManager {
    private long currentDate = 0;
    private PriorityQueue<Event> queue = new PriorityQueue<>();
    private final List<Event> initialEvents = new ArrayList<>();
    private boolean recordingInitial = true;

    /** Reset to time 0 and clear any scheduled events. */
    public void clear() {
        currentDate = 0;
        queue.clear();
        recordingInitial = true;
        initialEvents.clear();
    }

    /** @return the current simulation date. */
    public long getCurrentDate() { return currentDate; }

    /** Add an event. If still recording initial, also snapshot it for restart. */
    public void addEvent(Event e) {
        queue.add(e);
        if (recordingInitial) {
            initialEvents.add(e);
        }
    }

    /**
     * Advance time by 1 and execute all events scheduled for this date.
     */
    public void next() {
        // first tick marks the end of initial recording phase
        recordingInitial = false;
        currentDate++;
        while (!queue.isEmpty() && queue.peek().getDate() <= currentDate) {
            Event e = queue.poll();
            if (e.getDate() == currentDate) {
                e.execute();
            }
        }
    }

    /** @return true if no remaining events. */
    public boolean isFinished() { return queue.isEmpty(); }

    /**
     * Restart the manager: reset time to 0 and rebuild the initial schedule.
     * Note: the caller is responsible for also restoring the model state.
     */
    public void restart() {
        currentDate = 0;
        queue.clear();
        recordingInitial = false; // initial events have already been recorded
        for (Event e : initialEvents) {
            queue.add(e);
        }
    }
}

