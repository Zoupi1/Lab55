import simulator.Event;
import simulator.EventQueue;
import simulator.Simulator;
import simulator.StopEvent;
import store.event.StoreCloseEvent;
import store.event.StoreStartEvent;
import store.event.StoreCloseEvent;
import store.state.StoreState;
import store.view.StoreView;;

public class MainDriver {

	public static void main(String[] args) {

		long TIME_SEED = 1234; // Seed to generate random number
		int MAX_CUSTOMERS = 5; // Maximum number of costumers allowed in store at once
		int MAX_REGISTERS = 2; // Maximum number of registers available in store
		int TIME_STORE_CLOSE = 10; // At what time store closes
		double ARRIVAL_SPEED = 1; // Speed of which costumers arrive at
		double MIN_PICKING_TIME = 0.5; // Minimum time a costumer can pick items in
		double MAX_PICKING_TIME = 1; // Maximum time a costumer can pick items in
		double MIN_CHECKOUT_TIME = 2; // Minimum time a costumer can checkout in
		double MAX_CHECKOUT_TIME = 3; // Time a costumer can checkout in

		// Create instances of various objects
		EventQueue eventQueue = new EventQueue();
		StoreState state = new StoreState(TIME_SEED, MAX_CUSTOMERS, MAX_REGISTERS, TIME_STORE_CLOSE,
										  ARRIVAL_SPEED, MIN_PICKING_TIME, MAX_PICKING_TIME, MIN_CHECKOUT_TIME, 
										  MAX_CHECKOUT_TIME, eventQueue);
		StoreView view = new StoreView(state);
		
		// Create and add events
		eventQueue.addEvent(new StoreStartEvent(state));
		eventQueue.addEvent(new StoreCloseEvent(state, TIME_STORE_CLOSE));
		eventQueue.addEvent(new StopEvent(state, 99));
		
		state.addObserver(view);

		// Run simulator
		new Simulator(state, eventQueue).run();
	}

}
