package selectors;
import java.text.ParseException;

public enum Sauce {
	Cheese("Сырный"),
	Barbecue("Барбекю"),
	Teriyaki("Тэрияки");

	String name;

	Sauce(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static Sauce parse(String sauce) throws ParseException {
		switch (sauce) {
			case "Сырный":
				return Sauce.Cheese;
			case "Барбекю":
				return Sauce.Barbecue;
			case "Тэрияки":
				return Sauce.Teriyaki;
			default:
				throw new ParseException(sauce, 0);
		}
	}
}
