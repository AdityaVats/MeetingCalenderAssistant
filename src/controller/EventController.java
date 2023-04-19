package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Event;
import service.EventService;

public class EventController {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String eventStr = "";
		EventService eventService = new EventService();
		List<Event> events = new ArrayList<>();
		while(true){
			eventStr = sc.nextLine();
			if(eventStr.equals(""))	break;
			String[] parts = eventStr.split(",");
			String subject = parts[0].trim();
			String type = parts[1].trim();
			int durationInMin = Integer.parseInt(parts[2].trim());
			int priority = Integer.parseInt(parts[3].trim());
			Event event = new Event(priority,durationInMin, subject, type);
			events.add(event);
		}
		
		eventService.assignBatchEvents(events);
		
		
	}
	
	

}
