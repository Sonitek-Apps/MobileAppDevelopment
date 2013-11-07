
package com.example.mada_ueb03;

public class ToDoTask {

	private static final String INVALID_PRIORITY_VALUE = "Invalid Priority Value";
	private static final String TITLE_NOT_NULL_OR_EMPTY = "Title darf nicht leer oder NULL sein";
	private static final String DES_NOT_NULL = "Title darf nicht NULL sein";
	
	private String title;
	private String description;	
	private int priority;

	public ToDoTask(String title, String description, int priority) {

		this.title = title;
		this.description = description;
		this.priority = priority;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(title.isEmpty() || title == null)
			throw new NullPointerException(TITLE_NOT_NULL_OR_EMPTY);
		this.title = title;
	}

	public String getDescription() {
		if(description == null)
			throw new NullPointerException(DES_NOT_NULL);
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) throws InvalidPrioException {
		if(priority < 1 || priority > 3)
			throw new InvalidPrioException(INVALID_PRIORITY_VALUE);
		this.priority = priority;
	}

}

