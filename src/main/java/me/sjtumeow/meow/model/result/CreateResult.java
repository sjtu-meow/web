package me.sjtumeow.meow.model.result;

public class CreateResult {
	protected boolean result;
	protected Long id;
	
	public CreateResult() {
		this.result = false;
	}
	
	public CreateResult(Long id) {
		this.result = true;
		this.id = id;
	}

	public boolean isResult() {
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
