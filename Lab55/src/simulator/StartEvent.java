package simulator;


import store.state.StoreState;

public abstract class StartEvent extends Event {

	/**
	 * 
	 * @param state
	 */
	public StartEvent(SimState state) {
		super(state);
		this.executeTime = 0;
		
		
	}

	public abstract void runEvent();
}
