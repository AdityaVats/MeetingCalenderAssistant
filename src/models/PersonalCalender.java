package models;

import java.util.ArrayList;
import java.util.List;

public class PersonalCalender {

	private List<Event> meetings;

	public PersonalCalender() {
		meetings = new ArrayList<>();
	}
	public List<Event> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Event> meetings) {
		this.meetings = meetings;
	}
	

}
