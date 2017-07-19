package me.sjtumeow.meow.model.result;

public class LoginTokenResult {
    protected String token;

    public LoginTokenResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
