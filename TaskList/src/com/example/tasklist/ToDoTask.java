
package com.example.tasklist;

import java.util.Date;
import java.util.GregorianCalendar;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="task")
public class ToDoTask implements Comparable<ToDoTask>{
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;	
	@DatabaseField(foreign=true)
	private Priorit�t priority;
	@DatabaseField(foreign=true)
	private Kategorie kategorie;	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private GregorianCalendar ablaufDatum;
	
	public ToDoTask(String title, String description, Priorit�t priority, Kategorie kategorie, GregorianCalendar ablaufDatum) {
		this.kategorie = kategorie;
		this.title = title;
		this.description = description;
		this.priority = priority;
		setAblaufDatum(ablaufDatum);
	}

	public int getID(){
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priorit�t getPriority() {
		return priority;
	}

	/*
	 * setzt prioritaet
	 */
	public void setPriority(Priorit�t priority){
		this.priority = priority;
	}
	
	@Override
	 public int compareTo(ToDoTask task) {
	        return this.priority.compareTo(task.priority);
	    }

	/**
	 * @return the kategorie
	 */
	public Kategorie getKategorie() {
		return kategorie;
	}

	/**
	 * @param kategorie the kategorie to set
	 */
	public void setKategorie(Kategorie kategorie) {
		this.kategorie = kategorie;
	}

	@Override
	public boolean equals(Object o) {
		ToDoTask task = (ToDoTask) o;
		
		if (this.id == task.getID()) return true;
		else return false;
	}

	/**
	 * @return the ablaufDatum
	 */
	public GregorianCalendar getAblaufDatum() {
		return ablaufDatum;
	}

	/**
	 * @param ablaufDatum the ablaufDatum to set
	 */
	public void setAblaufDatum(GregorianCalendar ablaufDatum) {
		this.ablaufDatum = ablaufDatum;
	}

}
