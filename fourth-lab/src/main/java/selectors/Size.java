package selectors;
import java.text.ParseException;

public enum Size {
	Small("Маленькая"),
	Medium("Средняя"),
	Big("Большая");

	String name;

	Size(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static Size parse(String size) throws ParseException {
		switch (size) {
			case "Маленькая":
				return Size.Small;
			case "Средняя":
				return Size.Medium;
			case "Большая":
				return Size.Big;
			default:
				throw new ParseException(size, 0);
		}
	}
}
