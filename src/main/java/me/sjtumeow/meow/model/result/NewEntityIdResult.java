package me.sjtumeow.meow.model.result;

public class NewEntityIdResult {
	protected Long id;
	
	public NewEntityIdResult(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
