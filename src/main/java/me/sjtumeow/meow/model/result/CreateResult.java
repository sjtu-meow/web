package me.sjtumeow.meow.model.result;

public class CreateResult {
    protected boolean result;
    protected Long id;
    protected String message;

    public CreateResult() {
        this.result = false;
    }

    public CreateResult(Long id) {
        this.result = true;
        this.id = id;
    }

    public CreateResult(String message) {
        this.result = false;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
