package sim.core;

/**
 * A discrete event scheduled at a given date. Subclasses implement execute()
 * and typically reschedule themselves via an {@link EventManager} to animate
 * continuously (t -> t + dt).
 */
public abstract class Event implements Comparable<Event> {
    private final long date;

    /**
     * Create an event.
     * @param date simulation date at which to execute the event
     */
    public Event(long date) {
        this.date = date;
    }

    /** @return the scheduled date */
    public long getDate() { return date; }

    /** Execute the event's action. */
    public abstract void execute();

    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}

