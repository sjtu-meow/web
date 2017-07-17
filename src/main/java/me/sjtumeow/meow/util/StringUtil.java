package me.sjtumeow.meow.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class StringUtil {
	
	public static final String likeEscapeCharacter = "\\";
	
	public static String replaceNull(String s) {
		return s == null ? "" : s;
	}
	
	public static String filterRichText(String html) {
		PolicyFactory sanitizer = Sanitizers.BLOCKS.and(Sanitizers.FORMATTING).and(Sanitizers.IMAGES).and(Sanitizers.STYLES);
		return sanitizer.sanitize(html);
	}
	
	public static String wrapLikeSubstr(String keyword) {
		return '%' + keyword.replace(likeEscapeCharacter, likeEscapeCharacter + likeEscapeCharacter)
        	.replace("%", likeEscapeCharacter + "%").replace("_", likeEscapeCharacter + "_") + '%';
	}
	
	public static Long tryStringToPosLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return 0L;
		}
	}
}
