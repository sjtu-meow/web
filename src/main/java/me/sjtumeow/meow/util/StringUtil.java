package me.sjtumeow.meow.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class StringUtil {
	public static String replaceNull(String s) {
		return s == null ? "" : s;
	}
	
	public static String RichTextFilter(String html) {
		PolicyFactory sanitizer = Sanitizers.BLOCKS.and(Sanitizers.FORMATTING).and(Sanitizers.IMAGES).and(Sanitizers.STYLES);
		return sanitizer.sanitize(html);
	}
}
