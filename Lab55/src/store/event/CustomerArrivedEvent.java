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
public class CustomerArrivedEvent extends Event {

	private String eventDescription = "Arrive";
	private String eventUserDescription;
	Customer customer;

	public CustomerArrivedEvent(StoreState state, double time) {
		super(state);
		super.eventDescription = eventDescription;
		this.executeTime = time;
		this.customer = state.createNewCustomer();
		super.eventUserDescription = customer.toString();

	}

	/**
	 * state.storeTime.timeNextCustomer() blir storetime objektet som vi skapar i
	 * storestate. state.customersInTotal blir antalet kunder i aff�ren.
	 * 
	 * state.getStoreOpen checks if the store is opened or closed. Only need to
	 * chekc if the store is oppened here since if the store is opened other events
	 * can still occour.
	 * 
	 * TODO: add customer id to every single event.
	 */
	@Override
	public void runEvent() {
		if (((StoreState) state).storeIsOpen()) {

			double newTimeCustomer = ((StoreState) state).getElapsedTime() + ((StoreState) state).getTimeNextCustomer();
			if (((StoreState) state).getCustomersInTotal() >= ((StoreState) state).getMaxCustomers()) {
				((StoreState) state).increaseCustomerDeniedByOne();
				addEventToQueue(new CustomerArrivedEvent((StoreState) state, newTimeCustomer));
			} else {
				double newPickTime = state.getElapsedTime() + ((StoreState) state).getTimeCustomerPick();
				addEventToQueue(new CustomerArrivedEvent((StoreState) state, newTimeCustomer));
				addEventToQueue(new PickEvent((StoreState) state, newPickTime, customer));
			}
		}
	}

}
