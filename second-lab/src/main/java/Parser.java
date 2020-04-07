public class Parser {
    public enum Token {
        DIV("div"),
        P("p"),
        TABLE("table"),
        TITLE("title"),
        AREA("area"),
        BR("br"),
        IMG("img"),
        INPUT("input"),
        LINK("link"),
        META("meta"),
        UNKNOWN("unknown tag");

        private String tagName;

        Token(String tagName) {
            this.tagName = tagName;
        }

        public String getTagName() {
            return tagName;
        }
    }

    public static Token processLine(String htmlElement) {
        htmlElement = htmlElement.replaceAll("</*(\\w+)>","$1");

        switch (htmlElement) {
            case "div":
                return Token.DIV;
            case "p":
                return Token.P;
            case "table":
                return Token.TABLE;
            case "title":
                return Token.TITLE;
            case "area":
                return Token.AREA;
            case "br":
                return Token.BR;
            case "img":
                return Token.IMG;
            case "input":
                return Token.INPUT;
            case "link":
                return Token.LINK;
            case "meta":
                return Token.META;
            default:
                return Token.UNKNOWN;
        }
    }
}
