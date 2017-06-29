package me.sjtumeow.meow.util;

public class FormatValidator {
	
	public static boolean checkPhone(String s) {
		return s != null && s.matches("1\\d{10}");
	}
	
	public static boolean checkPassword(String s) {
		return s != null && s.length() >= 6;
	}
}
