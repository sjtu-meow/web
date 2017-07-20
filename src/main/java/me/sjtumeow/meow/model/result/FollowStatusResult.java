package me.sjtumeow.meow.model.result;

public class FollowStatusResult {
    protected boolean isFollowing;

    public FollowStatusResult(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }
}
