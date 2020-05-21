package org.ilyabaykalov;

import model.Order;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ui.OrderPizza;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public final class ParametrizedOrderPizzaTest extends FestSwingJUnitTestCase {
    private FrameFixture dialog;
    static KernelStub kernelStub = new KernelStub();
    OrderStub request;
    OrderStub expected;

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

    @Parameters
    public static Collection<Object[]> data() {
        OrderStub ordinary = new OrderStub("Коломенская 12", "12:30", "Пепперони", "Большая", "Сок", "Сырный");
        return Arrays.asList(new Object[][]{
                {ordinary, ordinary},
                {new OrderStub("", "", "", "", "", ""), null}
        });
    }

    public ParametrizedOrderPizzaTest(OrderStub request, OrderStub expected) {
        this.request = request;
        this.expected = expected;
    }

    @Test
    public void test() {
        kernelStub.Reset();
        {
            visitTextBox(OrderPizza.TF_ADDRESS, request.address);
            visitTextBox(OrderPizza.TF_TIME, request.time);

            visitComboBox(OrderPizza.CMB_TITLE, request.title);
            visitComboBox(OrderPizza.CMB_SIZE, request.size);
            visitComboBox(OrderPizza.CMB_DRINK, request.drink);
            visitComboBox(OrderPizza.CMB_SAUCE, request.sauce);

            dialog.button(OrderPizza.BTN_SEND).click();
        }
        kernelStub.Await();

        OrderStub actual = OrderStub.FromOrder(kernelStub.GetOrder());
        assertEquals(expected, actual);
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
}

final class OrderStub {
    public String address, time, title, size, drink, sauce;

    public static OrderStub FromOrder(Order order) {
        if (order == null) {
            return null;
        }
        OrderStub stub = new OrderStub();
        stub.address = order.address;
        stub.time = order.time;
        stub.title = order.title.toString();
        stub.size = order.size.toString();
        stub.drink = order.drink.toString();
        stub.sauce = order.sauce.toString();
        return stub;
    }

    public OrderStub(String address, String time, String title, String size, String drink, String sauce) {
        this.address = address;
        this.time = time;
        this.title = title;
        this.size = size;
        this.drink = drink;
        this.sauce = sauce;
    }

    public OrderStub() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        OrderStub order = (OrderStub) obj;
        return new EqualsBuilder()
                .append(address, order.address)
                .append(time, order.time)
                .append(title, order.title)
                .append(size, order.size)
                .append(drink, order.drink)
                .append(sauce, order.sauce)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(address)
                .append(time)
                .append(title)
                .append(size)
                .append(drink)
                .append(sauce)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(address)
                .append(time)
                .append(title)
                .append(size)
                .append(drink)
                .append(sauce)
                .toString();
    }
}










