/**
 * 
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 * 
 */

package store.time;

public class StoreTime {
	private ExponentialRandomStream randomExcept;
	private UniformRandomStream randomUni;

	/**
	 * Construct a new Time Object
	 * 
	 * @param lambda
	 * @param seed
	 */

	public StoreTime(double lambda, long seed) {
		this.randomExcept = new ExponentialRandomStream(lambda, seed);
		this.randomUni = new UniformRandomStream(lambda, seed);

	}

	/**
	 * Returns time for next customer to arrive
	 * 
	 * @Return time for next customer
	 */
	public double timeNextCustomer() {
		return randomExcept.next();

	}

	/**
	 * Returns time duration for a pick event
	 * 
	 * @return duration for a pick event
	 */
	public double timeCustomerPick() {
		return randomUni.next();

	}

	/**
	 * Returns time duration for a checkout event
	 * 
	 * @return time duration for a checkout event
	 */
	public double timeCustomerCheckOut() {
		return randomUni.next();
	}
}
