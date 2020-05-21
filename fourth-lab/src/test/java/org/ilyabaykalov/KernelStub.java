package org.ilyabaykalov;

import model.Kernel;
import model.Order;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// This is a Kernel stub for the Ordering testing purposes
public class KernelStub implements Kernel {
	
	private static final int COUNT = 1;
	private CountDownLatch order_latch_ = new CountDownLatch(COUNT);
	private Order order = new Order();
	public final static Lock lock = new ReentrantLock();
	
	public Order GetOrder() {
		return order;
	}
	
	public void Reset() {
		order_latch_ = new CountDownLatch(COUNT);
		order = null;
	}
	
	public void Await() {
		try {
			order_latch_.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public final void update(Observable event_source, Object data) {
		order = (Order)data;
		Free();
	}

	public void Free() {
		order_latch_.countDown();
	}

	@Override
	public void ConnectToServer() {
	}

	@Override
	public void DisconnectFromServer() {
	}
}