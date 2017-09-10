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
        String html = "<script></script>";
        assertEquals("", StringUtil.filterRichText(html));
        String str = "<b>str</b>";
        assertEquals(str, StringUtil.filterRichText(str));
    }

    @Test
    public void extractHTMLSummary() throws Exception {
    }

    @Test
    public void wrapLikeSubstr() throws Exception {
        String keyword = "str";
        assertEquals("%str%",StringUtil.wrapLikeSubstr(keyword));
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