package me.sjtumeow.meow.model.result;

public class FailureMessageResult {
    protected String message;

    public FailureMessageResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
