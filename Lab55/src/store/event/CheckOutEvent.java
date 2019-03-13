package store.event;

import simulator.Event;
import store.state.Customer;
import store.state.StoreState;

/**
 * 
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 * 
 */
public class CheckOutEvent extends Event {
	
	
	private String eventDescription = "Checkout";
	private String eventUserDescription;
	private boolean isPeopleInQueue;
	Customer customer;

	/**
	 * Constructor if there are avaliable registers and the que is empty.
	 * 
	 * @param state    current store state
	 * @param time     event execute time
	 * @param customer event for this customer
	 */
	public CheckOutEvent(StoreState state, double time, Customer customer) {
		super(state);
		super.eventDescription = eventDescription;
		state.closeOneRegister();
		this.executeTime = time;
		this.customer = customer;
		super.eventUserDescription = customer.toString();
		this.isPeopleInQueue = false;
	}


	/**
	 * 
	 * Constructor for if there are customers in the que that are waiting to pay.
	 * 
	 * 
	 * @param state current store state
	 * @param time  event execute time
	 */
	public CheckOutEvent(StoreState state, double time) {
		super(state);
		state.closeOneRegister();
		this.executeTime = time;
		// Gets the first custommer in the queue and deletes it from the queue.
		this.customer = state.getFirst();
		this.isPeopleInQueue = true;

	}

	@Override
	public void runEvent() {
		double newExecuteTime = state.getElapsedTime() + ((StoreState)state).getTimeNextCustomerCheckout();
		if (isPeopleInQueue) {
			// Open up a new register.
			((StoreState)state).openNewRegister();
			// Checks to see if there are anymore customers in the queue.
			if (!((StoreState)state).getCheckOutQueueIsEmpty()) {
				addEventToQueue(new CheckOutEvent((StoreState)state, newExecuteTime));

			}

		} else {
			((StoreState)state).openNewRegister();
			// No new event since there are no people in the queue.
		}

	}
}
