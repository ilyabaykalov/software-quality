package model;

import java.util.Observable;
import java.util.Observer;

public interface Kernel extends Observer {
	abstract void ConnectToServer();
	abstract void DisconnectFromServer();
	@Override
	abstract public void update(Observable event_source, Object data);
}
