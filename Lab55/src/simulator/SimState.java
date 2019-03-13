package simulator;

import java.util.Observable;

/**
 * 
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 *
 * @version 1.0
 */
public abstract class SimState extends Observable {
	protected double elapsedTime;
	protected boolean simulatorIsRunning;
	protected EventQueue eventQueue;

	public SimState(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}
	
	public final void startSimulator() {
		simulatorIsRunning = true;
	}
	
	public final void stopSimulator() {
		setChanged();
		notifyObservers();
		simulatorIsRunning = false;
	}
	
	public final boolean simulatorIsRunning() {
		return this.simulatorIsRunning;
	}

	public double getElapsedTime() {
		return elapsedTime;
	}
	
	public final EventQueue getEventQueue() {
		return eventQueue;
	}
	abstract public void updateState(Event event);
	
	
	abstract public void runSim();
}
