package model;import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import selectors.Drink;
import selectors.Sauce;
import selectors.Size;
import selectors.Title;

import java.text.ParseException;

public final class Order {

    public String address;
    public String time;
    public Title title;
    public Size size;
    public Drink drink;
    public Sauce sauce;

    public Order() {
    }

    public Order(String address, String time, String title, String size, String drink, String sauce)
            throws IllegalArgumentException {
        this.address = address;
        this.time = time;
        try {
            this.title = Title.parse(title);
            this.size = Size.parse(size);
            this.drink = Drink.parse(drink);
            this.sauce = Sauce.parse(sauce);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Order order = (Order) obj;
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
