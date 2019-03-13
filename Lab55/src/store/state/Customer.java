/**
 * 
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 * 
 */

/**
 * In this class, the customer id will be returned every time the code is run. 
 */
package store.state;

public class Customer {

	private int id;
	
	public Customer(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return Customer id
	 */
	public String toString() {
		return Integer.toString(id);
	}

}
