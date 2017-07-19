package me.sjtumeow.meow.model.result;

public class LikeStatusResult {
	protected boolean isLiked;
	
	public LikeStatusResult(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
}
