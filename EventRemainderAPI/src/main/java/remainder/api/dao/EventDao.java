package remainder.api.dao;

import java.util.List;

import remainder.api.bean.*;

public interface EventDao 
{
	public List<Event> getEventDetails(int eventId) throws Exception;
	public List<Event> getAllEvents() throws Exception ;
	public int insertEvent(Event event) throws Exception;
	public int updateEvent(int eventId,Event event) throws Exception;
	public int deleteEvent(int eventId) throws Exception;
}
