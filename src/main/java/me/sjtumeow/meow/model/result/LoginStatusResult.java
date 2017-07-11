package me.sjtumeow.meow.model.result;

public class LoginStatusResult {
	protected boolean isLoggedIn;
	
	public LoginStatusResult(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
}
