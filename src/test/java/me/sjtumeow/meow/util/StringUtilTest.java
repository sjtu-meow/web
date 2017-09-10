package me.sjtumeow.meow.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void replaceNull() throws Exception {
        String str = "str";
        assertEquals(str, StringUtil.replaceNull(str));
        assertEquals("", StringUtil.replaceNull(null));
    }

    @Test
    public void filterRichText() throws Exception {
        assertEquals("", StringUtil.filterRichText("<script>alert('hello')</script>"));
        assertEquals("text", StringUtil.filterRichText("<a href=\"http://example.com\">text</a>"));
        assertEquals("<p>blocks</p>", StringUtil.filterRichText("<p>blocks</p>"));
        assertEquals("<b>formatting</b>", StringUtil.filterRichText("<b>formatting</b>"));
        assertEquals("<img src=\"http://lorempixel.com/50/50\" />", StringUtil.filterRichText("<img src=\"http://lorempixel.com/50/50\" />"));
        assertEquals("<div style=\"color: #3c3;\">styled div</div>", StringUtil.filterRichText("<div style=\"color: #3c3;\">styled div</div>"));
    }

    @Test
    public void extractHTMLSummary() throws Exception {
        assertEquals("paragraph 1", StringUtil.extractHTMLSummary("<p>paragraph 1</p><p>paragraph 2</p>"));
        assertEquals("", StringUtil.extractHTMLSummary("<p>malformed data"));
    }

    @Test
    public void wrapLikeSubstr() throws Exception {
        assertEquals("%str%",StringUtil.wrapLikeSubstr("str"));
        assertEquals("%\_99\%%",StringUtil.wrapLikeSubstr("_99%"));
    }

    @Test
    public void tryStringToPosLong() throws Exception {
        assertEquals(Long.valueOf(123L), StringUtil.tryStringToPosLong("123"));
        assertEquals(Long.valueOf(0L), StringUtil.tryStringToPosLong("abc"));
    }

    @Test
    public void parseReportStatus() throws Exception {
        assertFalse(StringUtil.parseReportStatus("open"));
        assertTrue(StringUtil.parseReportStatus("closed"));
        assertNull(StringUtil.parseReportStatus("invalid status"));
    }

}