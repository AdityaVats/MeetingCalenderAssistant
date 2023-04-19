package service;

import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import data.Const;
import data.UserData;
import models.CorporateCalender;
import models.Event;
import models.PersonalCalender;

public class EventService {
	
	public EventService() {
		UserData.corporateCalender = new CorporateCalender();
		UserData.personalCalender = new PersonalCalender();
	}
	
	
	public void assignBatchEvents(List<Event> events) {
		Map<Boolean,List<Event>> mapByEventType = 
				events.stream()
			          .collect(Collectors.partitioningBy(
					  (event) -> event.getEventType().equals(Const.CORPORATE_EVENT)
					  ));
		
		Comparator<Event> comparator = 		(e1,e2) -> e1.getPriority() == e2.getPriority() ? 
				e2.getDuration() - e1.getDuration() :
				e1.getPriority() - e2.getPriority();

		PriorityQueue<Event> corporatePQ = new PriorityQueue<>(comparator);
		corporatePQ.addAll(mapByEventType.get(true));
		PriorityQueue<Event> personalPQ = new PriorityQueue<>(comparator);
		personalPQ.addAll(mapByEventType.get(false));
		
		PriorityQueue<Event> discardedPQ = new PriorityQueue<>(comparator);
		for(int half=0;half<2;half++) {
			List<Event> corporateCalenderEvents = new ArrayList<>();
			
			int time = 4*60;
			while(!corporatePQ.isEmpty()) {
				Event event = corporatePQ.poll();
				if(event.getDuration() > time) {
					discardedPQ.offer(event);
					continue;
				}
				corporateCalenderEvents.add(event);
				time -= event.getDuration();
			}
			while(!discardedPQ.isEmpty()) {
				corporatePQ.offer(discardedPQ.poll());
			}
			LocalTime startTime;
			if(half == 0) {
				startTime = LocalTime.of(8, 0);
			} else {
				startTime = LocalTime.of(13, 0);
			}
			// assign event time to all events now
			for(Event event:corporateCalenderEvents) {
				event.setStartTime(startTime);
				event.setEndTime(startTime.plusMinutes(event.getDuration()));
				startTime = startTime.plusMinutes(event.getDuration());
			}
			UserData.corporateCalender.getMeetings().addAll(corporateCalenderEvents);
			
		}
		
		
		PriorityQueue<Event> pq = new PriorityQueue<>(comparator);
		pq.addAll(mapByEventType.get(false));
		pq.addAll(corporatePQ);
		
		int corporateTime = Const.HOURS_FOR_CORPORATE_EVENTS;
		
		for(int half=0;half<2;half++) {
			int time = 4*60;
			List<Event> personalCalenderEvents = new ArrayList<>();
			
			while(!pq.isEmpty()) {
				Event event = pq.poll();
				if(event.getDuration() > time || (event.getEventType().equals(Const.CORPORATE_EVENT) && event.getDuration() > corporateTime ) ) {
					discardedPQ.offer(event);
					continue;
				}
				personalCalenderEvents.add(event);
				if(event.getEventType().equals(Const.CORPORATE_EVENT)){
					corporateTime -= event.getDuration();
				}
				time -= event.getDuration();
			}
			while(!discardedPQ.isEmpty()) {
				pq.offer(discardedPQ.poll());
			}
			// assign event time to all events now
			LocalTime startTime;
			if(half == 0) {
				startTime = LocalTime.of(8, 0);
			} else {
				startTime = LocalTime.of(13, 0);
			}
			// assign event time to all events now
			for(Event event:personalCalenderEvents) {
				event.setStartTime(startTime);
				event.setEndTime(startTime.plusMinutes(event.getDuration()));
				startTime = startTime.plusMinutes(event.getDuration());
			}
			UserData.personalCalender.getMeetings().addAll(personalCalenderEvents);
		}
		System.out.println("____CORPORATE CALENDER EVENTS_______");
		
		System.out.println(UserData.corporateCalender.getMeetings());
		System.out.println("____PERSONAL CALENDER EVENTS_______");
		System.out.println(UserData.personalCalender.getMeetings());
		
		List<Event> discardedEvent = new ArrayList<>();
		discardedEvent.addAll(pq);
		System.out.println("____DISCARDED EVENTS_______");
		System.out.println(discardedEvent);
	}

}
