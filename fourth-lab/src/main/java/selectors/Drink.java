package selectors;

import java.text.ParseException;

public enum Drink {
    Tea("Чай"),
    Coffee("Кофе"),
    Juice("Сок"),
    Cola("Кола");

    String name;

    Drink(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Drink parse(String drink) throws ParseException {
        switch (drink) {
            case "Чай":
                return Drink.Tea;
            case "Кофе":
                return Drink.Coffee;
            case "Сок":
                return Drink.Juice;
            case "Кола":
                return Drink.Cola;
            default:
                throw new ParseException(drink, 0);
        }
    }
}