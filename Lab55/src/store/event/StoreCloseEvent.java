package store.event;

import simulator.Event;
import store.state.StoreState;

/**
 *
 * @author Nour Aldein Bahtite
 * @author Philip Eriksson
 * @author Rickard Bemm
 * @author André Christofferson
 *
 */
public class StoreCloseEvent extends Event {
	private String eventUserDescription = "Close";
	public StoreCloseEvent(StoreState state, double time) {
		super(state);
		super.eventDescription = eventDescription;
		super.eventUserDescription = eventUserDescription;
		this.executeTime = time;
	}

	public void runEvent() {
		((StoreState) state).closeStore();
	}

}
