package me.sjtumeow.meow.util;

import me.sjtumeow.meow.model.Item;

public class FormatValidator {
	
	public static boolean checkNonNegativeInt(Integer x) {
		return x != null && x >= 0;
	}
	public static boolean checkPositiveInt(Integer x) {
		return x != null && x > 0;
	}
	
	public static boolean checkPhone(String s) {
		return s != null && s.matches("^1\\d{10}$");
	}
	
	public static boolean checkPassword(String s) {
		return s != null && s.length() >= 6;
	}
	
	public static boolean checkSmsCode(String s) {
		return s != null && s.matches("^\\d{6}$");
	}
	
	public static boolean checkItemType(Integer x) {
		return x != null && (x == Item.ITEM_TYPE_MOMENT || x == Item.ITEM_TYPE_ARTICLE || x == Item.ITEM_TYPE_QUESTION || x == Item.ITEM_TYPE_ANSWER);
	}
}
