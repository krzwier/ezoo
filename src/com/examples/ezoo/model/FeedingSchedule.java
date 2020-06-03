package com.examples.ezoo.model;

public class FeedingSchedule {
	
	
	private long schedule_ID = 0L;
	private String feeding_time = "";
	private String recurrence = "";
	private String food = "";
	private String notes = "";
	
	public FeedingSchedule() {}
	
	public FeedingSchedule(long schedule_ID, String feeding_time, String recurrence, String food, String notes) {
		super();
		this.schedule_ID = schedule_ID;
		this.feeding_time = feeding_time;
		this.recurrence = recurrence;
		this.food = food;
		this.notes = notes;
	}
	
	public long getSchedule_ID() {
		return schedule_ID;
	}
	public void setSchedule_ID(long schedule_ID) {
		this.schedule_ID = schedule_ID;
	}
	public String getFeeding_time() {
		return feeding_time;
	}
	public void setFeeding_time(String feeding_time) {
		this.feeding_time = feeding_time;
	}
	public String getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeedingSchedule other = (FeedingSchedule) obj;
		if (feeding_time == null) {
			if (other.feeding_time != null)
				return false;
		} else if (!feeding_time.equals(other.feeding_time))
			return false;
		if (food == null) {
			if (other.food != null)
				return false;
		} else if (!food.equals(other.food))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (recurrence == null) {
			if (other.recurrence != null)
				return false;
		} else if (!recurrence.equals(other.recurrence))
			return false;
		if (schedule_ID != other.schedule_ID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FeedingSchedule [schedule_ID=" + schedule_ID + ", feeding_time=" + feeding_time + ", recurrence="
				+ recurrence + ", food=" + food + ", notes=" + notes + "]";
	}
	

}
