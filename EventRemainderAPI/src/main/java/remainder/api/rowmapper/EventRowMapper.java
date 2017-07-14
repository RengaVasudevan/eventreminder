package remainder.api.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import remainder.api.bean.*;

public class EventRowMapper implements RowMapper<Event> {

	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {

		Event event = new Event();
		event.setEventId(rs.getInt("eventId"));
		event.setEventName(rs.getString("eventName"));
		event.setEventOrganizer(rs.getString("eventOrganizer"));
		event.setEventDescription(rs.getString("eventDescription"));
		event.setEventStartDate(rs.getString("eventStartDate"));
		event.setEventStartTime(rs.getString("eventStartTime"));
		event.setEventEndDate(rs.getString("eventEndDate"));
		event.setEventEndTime(rs.getString("eventEndTime"));
		event.setEventLocation(rs.getString("eventLocation"));
		event.setParticipantTeam(rs.getString("participantTeam"));

		return event;
	}

}
