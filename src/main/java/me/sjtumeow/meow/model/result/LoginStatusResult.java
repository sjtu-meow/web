package me.sjtumeow.meow.model.result;

public class LoginStatusResult {
    protected boolean isLoggedIn;
    protected boolean isAdmin;

    public LoginStatusResult(boolean isLoggedIn, boolean isAdmin) {
        this.isLoggedIn = isLoggedIn;
        this.isAdmin = isAdmin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
