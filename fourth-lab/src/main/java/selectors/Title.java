package selectors;

import java.text.ParseException;

public enum Title {
    Classic("Классическая"),
    ChickenBBQ("Цыпленок барбекю"),
    Pepperoni("Пепперони"),
    Seasons("Времена года"),
    Margaret("Маргарита");

    String name;

    Title(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Title parse(String title) throws ParseException {
        switch (title) {
            case "Классика":
                return Title.Classic;
            case "Цыпленок барбекю":
                return Title.ChickenBBQ;
            case "Пепперони":
                return Title.Pepperoni;
            case "Времена года":
                return Title.Seasons;
            case "Маргарита":
                return Title.Margaret;
            default:
                throw new ParseException(title, 0);
        }
    }
}
