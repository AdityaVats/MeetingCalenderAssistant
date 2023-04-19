package models;

import java.util.ArrayList;
import java.util.List;

public class CorporateCalender {
	
	private List<Event> meetings;
	
	public CorporateCalender() {
		meetings = new ArrayList<>();
	}

	public List<Event> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Event> meetings) {
		this.meetings = meetings;
	}
	

}
