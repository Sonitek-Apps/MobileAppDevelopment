package com.example.tasklist;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="priority")
public class Priorit�t implements Comparable<Priorit�t> {

	@DatabaseField
	private String name;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int value;
	@DatabaseField
	private Date ablaufDatum;  

	public Priorit�t(String name, int id, int value, Date ablaufDatum) {
		setName(name);
		setId(id);
		setValue(value);
		setAblaufDatum(ablaufDatum);
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

	/**
	 * @return the ablaufDatum
	 */
	public Date getAblaufDatum() {
		return ablaufDatum;
	}

	/**
	 * @param ablaufDatum the ablaufDatum to set
	 */
	public void setAblaufDatum(Date ablaufDatum) {
		this.ablaufDatum = ablaufDatum;
	}

}
