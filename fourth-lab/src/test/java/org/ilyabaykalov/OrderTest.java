package org.ilyabaykalov;

import model.Order;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void basic_validation() {
        Order expected = new Order("Юшкова 18а", "22:30", "Цыпленок барбекю", "Большая", "Чай", "Барбекю");
        Order actual = new Order("Юшкова 18а", "22:30", "Цыпленок барбекю", "Большая", "Чай", "Барбекю");
        assertEquals(expected, actual);
    }

    @Test
    public void parsing_validation() {
        String address = "Юшкова 18а",
                time = "12:30",
                title = "Пепперони",
                size = "Большая",
                drink = "Сок",
                sauce = "Барбекю";
        Order order = new Order(address, time, title, size, drink, sauce);

        assertEquals(address, order.address);
        assertEquals(time, order.time);
        assertEquals(title, order.title.toString());
        assertEquals(size, order.size.toString());
        assertEquals(drink, order.drink.toString());
        assertEquals(sauce, order.sauce.toString());
    }
}