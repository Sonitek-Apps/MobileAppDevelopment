package com.example.tasklist;

public class Priorit�t implements Comparable<Priorit�t> {

	private String name;
	private int id;
	private int value;

	public Priorit�t(String name, int id, int value) {
		setName(name);
		setId(id);
		setValue(value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Priorit�t prio) {
		
		if (this.value == prio.getValue()) {
			return 0;
		} else if (this.value > prio.getValue()) {
			return 1;
		} else {
			return -1;
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
