package org.ilyabaykalov;

import model.Kernel;
import model.Order;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;

public class KernelStub implements Kernel {
	
	private static final int COUNT = 1;
	private CountDownLatch countDownLatch = new CountDownLatch(COUNT);
	private Order order = new Order();

	public Order GetOrder() {
		return order;
	}
	
	public void Reset() {
		countDownLatch = new CountDownLatch(COUNT);
		order = null;
	}
	
	public void Await() {
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void update(Observable event_source, Object data) {
		order = (Order)data;
		run();
	}

	public void run() {
		countDownLatch.countDown();
	}

	@Override
	public void ConnectToServer() {
	}

	@Override
	public void DisconnectFromServer() {
	}
}