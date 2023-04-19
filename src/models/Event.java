package models;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {
	
	private int priority;
	private int duration;
	private String eventSubject;
	private String eventType;
	

	private LocalTime startTime;
	private LocalTime endTime;
	
	
	
	public Event(int priority, int duration, String eventSubject, String eventType) {
		super();
		this.priority = priority;
		this.duration = duration;
		this.eventSubject = eventSubject;
		this.eventType = eventType;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getEventSubject() {
		return eventSubject;
	}
	public void setEventSubject(String eventSubject) {
		this.eventSubject = eventSubject;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public String toString() {
		return startTime + " to " + endTime + "-" + eventSubject + "("+ eventType +")\n";
	}

}
