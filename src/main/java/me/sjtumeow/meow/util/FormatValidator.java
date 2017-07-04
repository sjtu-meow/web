package me.sjtumeow.meow.util;

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
}
