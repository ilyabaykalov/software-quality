package model;

import java.util.Observable;

public class OrderingEventSource extends Observable {
	public OrderingEventSource(Kernel target) {
		addObserver(target);
	}

	public void SendOrder(Object data) {
		setChanged();
		notifyObservers(data);
	}
}
