import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatementCoverage {

    @Test
    public void testDiv() {
        assertEquals(Parser.Token.DIV, Parser.processLine("<div>"));
    }

    @Test
    public void testDivWithSlash() {
        assertEquals(Parser.Token.DIV, Parser.processLine("</div>"));
    }

    @Test
    public void testP() {
        assertEquals(Parser.Token.P, Parser.processLine("</p>"));
    }

    @Test
    public void testPWithSlash() {
        assertEquals(Parser.Token.P, Parser.processLine("</p>"));
    }

    @Test
    public void testTable() {
        assertEquals(Parser.Token.TABLE, Parser.processLine("</table>"));
    }

    @Test
    public void testTableWithSlash() {
        assertEquals(Parser.Token.TABLE, Parser.processLine("</table>"));
    }

    @Test
    public void testTitle() {
        assertEquals(Parser.Token.TITLE, Parser.processLine("</title>"));
    }

    @Test
    public void testTitleWithSlash() {
        assertEquals(Parser.Token.TITLE, Parser.processLine("</title>"));
    }

    @Test
    public void testArea() {
        assertEquals(Parser.Token.AREA, Parser.processLine("</area>"));
    }

    @Test
    public void testAreaWithSlash() {
        assertEquals(Parser.Token.AREA, Parser.processLine("</area>"));
    }

    @Test
    public void testBr() {
        assertEquals(Parser.Token.BR, Parser.processLine("</br>"));
    }

    @Test
    public void testBrWithSlash() {
        assertEquals(Parser.Token.BR, Parser.processLine("</br>"));
    }

    @Test
    public void testImg() {
        assertEquals(Parser.Token.IMG, Parser.processLine("</img>"));
    }

    @Test
    public void testImgWithSlash() {
        assertEquals(Parser.Token.IMG, Parser.processLine("</img>"));
    }

    @Test
    public void testInput() {
        assertEquals(Parser.Token.INPUT, Parser.processLine("</input>"));
    }

    @Test
    public void testInputWithSlash() {
        assertEquals(Parser.Token.INPUT, Parser.processLine("</input>"));
    }

    @Test
    public void testLink() {
        assertEquals(Parser.Token.LINK, Parser.processLine("</link>"));
    }

    @Test
    public void testLinkWithSlash() {
        assertEquals(Parser.Token.LINK, Parser.processLine("</link>"));
    }

    @Test
    public void testMeta() {
        assertEquals(Parser.Token.META, Parser.processLine("</meta>"));
    }

    @Test
    public void testMetaWithSlash() {
        assertEquals(Parser.Token.META, Parser.processLine("</meta>"));
    }
    
    @Test
    public void testUnknownTag() {
        assertEquals(Parser.Token.UNKNOWN, Parser.processLine("</unknown tag>"));
    }

    @Test
    public void testUnknownTagWithSlash() {
        assertEquals(Parser.Token.UNKNOWN, Parser.processLine("</unknown tag>"));
    }

    @Test
    public void testNotTag() {
        assertEquals(Parser.Token.UNKNOWN, Parser.processLine("trash"));
    }

    @Test
    public void testDivValue() {
        assertEquals("div", Parser.Token.DIV.getTagName());
    }
}
