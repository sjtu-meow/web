package me.sjtumeow.meow.model.result;

public class QiniuTokenResult {
    protected String token;
    protected Integer expireTime;

    public QiniuTokenResult(String token, Integer expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

}
