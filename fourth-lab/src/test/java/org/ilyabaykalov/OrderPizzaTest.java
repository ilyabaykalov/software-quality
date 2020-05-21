package org.ilyabaykalov;

import model.Order;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;
import ui.OrderPizza;

import java.awt.*;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public final class OrderPizzaTest extends FestSwingJUnitTestCase {

	private FrameFixture dialog;
	static KernelStub kernelStub = new KernelStub();

	@Override
	protected void onSetUp() {
		dialog = new FrameFixture(robot(), runOrderDialogue());
		dialog.show(new Dimension(450, 541));
	}

	@RunsInEDT
	private static OrderPizza runOrderDialogue() {
		return execute(new GuiQuery<OrderPizza>() {
			protected OrderPizza executeInEDT() {
				return new OrderPizza(kernelStub);
			}
		});
	}

	public void visitTextBox(String name, String text) {
		JTextComponentFixture the_box = dialog.textBox(name);
		the_box.setText(text);
		assertThat(the_box.text()).contains(text);
	}

	public void visitComboBox(String name, String text) {
		if (text.isEmpty())
			return;
		JComboBoxFixture the_box = dialog.comboBox(name);
		the_box.selectItem(text);
		int idx = Arrays.asList(the_box.contents()).indexOf(text);
		assertThat(the_box.valueAt(idx)).isEqualTo(text);
	}

	@Test
	public void simple() {
		kernelStub.Reset();
		{
			visitTextBox(OrderPizza.TF_ADDRESS, "Юшкова 18а");
			visitTextBox(OrderPizza.TF_TIME, "16:00");

			visitComboBox(OrderPizza.CMB_TITLE, "Пепперони");
			visitComboBox(OrderPizza.CMB_SIZE, "Маленькая");
			visitComboBox(OrderPizza.CMB_DRINK, "Кола");
			visitComboBox(OrderPizza.CMB_SAUCE, "Барбекю");

			dialog.button(OrderPizza.BTN_SEND).click();
		}
		kernelStub.Await();
	}

	@Test
	public void validation() {
		Order request = new Order("Колменская 12", "12:30", "Цыпленок барбекю", "Большая", "Чай", "Тэрияки");
		kernelStub.Reset();
		{
			visitTextBox(OrderPizza.TF_ADDRESS, request.address);
			visitTextBox(OrderPizza.TF_TIME, request.time);

			visitComboBox(OrderPizza.CMB_TITLE, request.title.toString());
			visitComboBox(OrderPizza.CMB_SIZE, request.size.toString());
			visitComboBox(OrderPizza.CMB_DRINK, request.drink.toString());
			visitComboBox(OrderPizza.CMB_SAUCE, request.sauce.toString());

			dialog.button(OrderPizza.BTN_SEND).click();
		}
		kernelStub.Await();

		Order actual = kernelStub.GetOrder();
		assertEquals(request, actual);
	}

	@Test
	public void null_test() {
		kernelStub.Reset();
		{
			dialog.button(OrderPizza.BTN_SEND).click();
		}
		kernelStub.Await();

		Order actual = kernelStub.GetOrder();
		assertNull(actual);
	}
}