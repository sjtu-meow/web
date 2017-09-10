package me.sjtumeow.meow.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {
    @Test
    public void replaceNull() throws Exception {
        String str = "str";
        Assert.assertEquals(str, StringUtil.replaceNull(str));
        Assert.assertEquals("", StringUtil.replaceNull(null));
    }

    @Test
    public void filterRichText() throws Exception {
        String html = "<script></script>";
        Assert.assertEquals("", StringUtil.filterRichText(html));
        String str = "<b>str</b>";
        Assert.assertEquals(str, StringUtil.filterRichText(str));
    }

    @Test
    public void extractHTMLSummary() throws Exception {
    }

    @Test
    public void wrapLikeSubstr() throws Exception {
    }

    @Test
    public void tryStringToPosLong() throws Exception {
    }

    @Test
    public void parseReportStatus() throws Exception {
    }

}