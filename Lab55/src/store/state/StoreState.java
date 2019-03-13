package store.state;

import simulator.Event;
import simulator.EventQueue;
import store.time.StoreTime;

/**
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 */
public class StoreState extends simulator.SimState {

	// Constants
	private final long TIME_SEED;
	private final int MAX_CUSTOMERS;
	private final int MAX_REGISTERS;
	private final double TIME_STORE_CLOSE;
	private final double ARRIVAL_SPEED;
	private final double MIN_PICKING_TIME;
	private final double MAX_PICKING_TIME;
	private final double MIN_CHECKOUT_TIME;
	private final double MAX_CHECKOUT_TIME;

	// Customer statistics
	private int customersPayed;
	private int customersInTotal;
	private int customersVisited;
	private int customersInQueue;
	private int customersDeniedEntry;

	// Checkout statistics
	private int registersOpen;
	private double queueTime;
	private double checkoutFreeTime;
	
	// Event descriptions
	private String eventDescription;
	private String customerWhoPerformedEvent;

	private boolean storeIsOpen;

	private FIFO<Customer> checkOutQueue;
	private StoreTime storeTime;
	private CreateCustomer customerSpawn;

	/**
	 * Construct an instance of StoreState
	 * 
	 * @param TIME_SEED         Seed to generate random number
	 * @param MAX_CUSTOMERS     Maximum number of costumers allowed in store at once
	 * @param MAX_REGISTERS     Maximum number of registers available in store
	 * @param TIME_STORE_CLOSE  At what time store closes
	 * @param ARRIVAL_SPEED     Speed of which costumers arrive at
	 * @param MIN_PICKING_TIME  Minimum time a costumer can pick items in
	 * @param MAX_PICKING_TIME  Maximum time a costumer can pick items in
	 * @param MIN_CHECKOUT_TIME Minimum time a costumer can checkout in
	 * @param MAX_CHECKOUT_TIME Maximum time a costumer can checkout in
	 */
	public StoreState(long TIME_SEED, int MAX_CUSTOMERS, int MAX_REGISTERS, double TIME_STORE_CLOSE,
			double ARRIVAL_SPEED, double MIN_PICKING_TIME, double MAX_PICKING_TIME, double MIN_CHECKOUT_TIME,
			double MAX_CHECKOUT_TIME, EventQueue eventQueue) {
		super(eventQueue);

		this.storeTime = new StoreTime(ARRIVAL_SPEED, TIME_SEED);
		this.checkOutQueue = new FIFO<Customer>();
		this.customerSpawn = new CreateCustomer();

		this.TIME_SEED = TIME_SEED;
		this.MAX_CUSTOMERS = MAX_CUSTOMERS;
		this.MAX_REGISTERS = MAX_REGISTERS;
		this.TIME_STORE_CLOSE = TIME_STORE_CLOSE;
		this.ARRIVAL_SPEED = ARRIVAL_SPEED;
		this.MIN_PICKING_TIME = MIN_PICKING_TIME;
		this.MAX_PICKING_TIME = MAX_PICKING_TIME;
		this.MIN_CHECKOUT_TIME = MIN_CHECKOUT_TIME;
		this.MAX_CHECKOUT_TIME = MAX_CHECKOUT_TIME;
	}

	/**
	 * Create a new customer
	 *
	 * @return newCustomer() method
	 */
	public Customer createNewCustomer() {
		return customerSpawn.newCustomer();
	}

	/**
	 * Get at what time store will close at.
	 * 
	 * @return what time store will close at
	 */
	public double getTimeStoreClose() {
		return TIME_STORE_CLOSE;
	}

	/**
	 * A new customer enters the store. The store can accept a new customer only if
	 * the store doesn't have the maximal number of customer in it
	 *
	 * @throws OpenRegisterFailedException else
	 */
	public void openNewRegister() {
		if (registersOpen < MAX_REGISTERS) {
			setChanged();
			notifyObservers();
			registersOpen++;
		} else {
			// TODO: throw new OpenRegisterFailedException()
		}
	}

	/**
	 * Close the store and allow the customers (if they found ) to pay for their
	 * things.
	 *
	 * @throws CloseRegisterFailedException else.
	 */
	public void closeOneRegister() {
		if (registersOpen > 0) {
			setChanged();
			notifyObservers();
			registersOpen--;
		} else {
			// TODO: throw new CloseRegisterFailedException()
		}
	}

	/**
	 * The store is open and can accept customers.
	 *
	 * @return storeIsOpen
	 */
	public boolean storeIsOpen() {
		return storeIsOpen;
	}

	/**
	 * The store is closed and doesn't accept new customers. Change storeIsOpen to
	 * false.
	 */
	public void closeStore() {
		if (storeIsOpen) {
			setChanged();
			notifyObservers();
			storeIsOpen = false;
		}
	}

	/**
	 * To open the store.
	 *
	 * StoreIsOpen change to true.
	 */
	public void openStore() {
		if (!storeIsOpen) {
			setChanged();
			notifyObservers();
			storeIsOpen = true;
		}
	}

	/**
	 * Increase the number of customers who couldn't enter the store by one.
	 */
	public void increaseCustomerDeniedByOne() {
		setChanged();
		notifyObservers();
		customersDeniedEntry++;
	}

	public StoreTime getStoreTime() {
		return storeTime;
	}

	/**
	 * Get the number of customers who wait on the queue to pay their things.
	 *
	 * @return checkOutQueue
	 */
	public FIFO<Customer> getCheckoutQueue() {
		return checkOutQueue;
	}

	/**
	 * Get the max number of customers in the store.
	 *
	 * @return MAX_CUSTOMERS
	 */
	public int getMaxCustomers() {
		return MAX_CUSTOMERS;
	}

	/**
	 * Get the number of all the customers who could and couldn't enter the store.
	 *
	 * @return customersInTotal
	 */
	public int getCustomersInTotal() {
		return customersInTotal;
	}

	/**
	 * Get the number of the customers which can be accepted in the store in the
	 * moment
	 *
	 * @return registersOpen
	 */
	public int getRegistersOpen() {
		return registersOpen;
	}

	/**
	 * If the check out queue is empty.
	 *
	 * @return isEmpty()
	 */
	public boolean getCheckOutQueueIsEmpty() {
		return checkOutQueue.isEmpty();
	}

	/**
	 * Get the first customer who waits in the check out queue for paying his things
	 *
	 * @return getFirst()
	 */
	public Customer getFirst() {
		return checkOutQueue.getFirst();
	}

	/**
	 * Get queue time between two customers.
	 *
	 * @return timeNextCustomer()
	 */
	public double getTimeNextCustomer() {
		return storeTime.timeNextCustomer();
	}

	/**
	 * Get check out time between two customers.
	 *
	 * @return timeCustomerCheckOut()
	 */
	public double getTimeNextCustomerCheckout() {
		return storeTime.timeCustomerCheckOut();
	}

	/**
	 * Get pick time for customers.
	 *
	 * @return timeCustomerPick()
	 */
	public double getTimeCustomerPick() {
		return storeTime.timeCustomerPick();
	}

	/**
	 * Add new customer in pay queue
	 *
	 * @param customer
	 */
	public void addCustomerInPayoutLine(Customer customer) {
		setChanged();
		notifyObservers();
		checkOutQueue.add(customer);
	}

	/**
	 * Get seed time.
	 *
	 * @return TIME_SEED
	 */
	public long getTIME_SEED() {
		return TIME_SEED;
	}

	/**
	 * Get the max number of customers.
	 *
	 * @return MAX_CUSTOMERS
	 */
	public int getMAX_CUSTOMERS() {
		return MAX_CUSTOMERS;
	}

	/**
	 * Get max number of registers.
	 *
	 * @return MAX_REGISTERS
	 */
	public int getMAX_REGISTERS() {
		return MAX_REGISTERS;
	}

	/**
	 * Get the speed of arrival.
	 *
	 * @return ARRIVAL_SPEED
	 */
	public double getARRIVAL_SPEED() {
		return ARRIVAL_SPEED;
	}

	/**
	 * Get the minimum time of picking.
	 *
	 * @return MIN_PICKING_TIME
	 */
	public double getMIN_PICKING_TIME() {
		return MIN_PICKING_TIME;
	}

	/**
	 * Get the maximum time of picking.
	 *
	 * @return MAX_PICKING_TIME
	 */
	public double getMAX_PICKING_TIME() {
		return MAX_PICKING_TIME;
	}

	/**
	 * Get the minimum time of check out.
	 *
	 * @return MIN_CHECKOUT_TIME
	 */
	public double getMIN_CHECKOUT_TIME() {
		return MIN_CHECKOUT_TIME;
	}

	/**
	 *
	 * Get the maximum time of check out.
	 *
	 * @return MAX_CHECKOUT_TIME
	 */
	public double getMAX_CHECKOUT_TIME() {
		return MAX_CHECKOUT_TIME;
	}

	/**
	 * Get the number of customers who paid for their things
	 *
	 * @return customersPayed
	 */
	public int getCustomersPayed() {
		return customersPayed;
	}

	/**
	 * Get the number of customers who visited the store
	 *
	 * @return customersVisited
	 */
	public int getCustomersVisited() {
		return customersVisited;
	}

	/**
	 * Get the number of the customers who wait in queue.
	 *
	 * @return customersInQueue
	 */
	public int getCustomersInQueue() {
		return customersInQueue;
	}

	/**
	 * Get the number of customers who couldn't go in the store.
	 *
	 * @return customersDeniedEntry
	 */
	public int getCustomersDeniedEntry() {
		return customersDeniedEntry;
	}


	/**
	 * Get the time while the checkout was empty
	 *
	 * @return checkoutFreeTime
	 */

	public double getCheckoutFreeTime() {
		return checkoutFreeTime;
	}

	/**
	 * Update the time when the checkout is down.
	 *
	 * @param deadRegisterTime
	 */
	public void uppdateRegistersDownTime(double deadRegisterTime) {
		setChanged();
		notifyObservers();
		checkoutFreeTime += deadRegisterTime;
	}

	/**
	 * Update the time that spent in queue
	 *
	 * @param peopleInQueueTime
	 */
	public void uppdateCustomersInQueueTime(double peopleInQueueTime) {
		setChanged();
		notifyObservers();
		queueTime += peopleInQueueTime;
	}

	@Override
	public void runSim() {
		setChanged();
		notifyObservers();
		startSimulator();
	}
	//FOR VIEW
	public double getCheckOutFreeTime() {
		return checkoutFreeTime;
	} 
	
	public double getQueueTime() {
		return queueTime;
	} 
	public double getElapsedTime() {
		return elapsedTime;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public String getCustomerWhoPerformedEvent() {
		return customerWhoPerformedEvent;
	}
	
	

	@Override
	public void updateState(Event event) {
		// TIME
		
		// Updates registers wasted time
		if (storeIsOpen) {
			checkoutFreeTime += registersOpen * (event.getExTime() - elapsedTime);
		}

		// Updates time that people have been standing in the queue
		queueTime += customersInQueue * (event.getExTime() - elapsedTime);

		// Sets time to be the time that the event was executed.
		elapsedTime += event.getExTime()-elapsedTime;
		
		//DESCRIPTION OF EVENT
		
		// Updates event that occured
		eventDescription = event.getEventDescription();
		
		// Updates which customer who performed the event.
		customerWhoPerformedEvent = event.getEventUserDescription();
		
		setChanged();
		notifyObservers();
	}

}
