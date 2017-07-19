package me.sjtumeow.meow.model.result;

public class FavoriteStatusResult {
	protected boolean isFavourite;
	
	public FavoriteStatusResult(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}
	
}
