package model;

import ui.OrderPizza;

import javax.swing.*;

public class Main {
	final static Kernel kernel = new KernelImpl();

	public static void main(String[] args) {
		try {
			new OrderPizza(kernel).start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occurred! See stack trace.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
}