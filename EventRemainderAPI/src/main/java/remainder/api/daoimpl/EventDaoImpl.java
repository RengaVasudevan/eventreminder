package remainder.api.daoimpl;

import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import remainder.api.bean.Event;
import remainder.api.dao.EventDao;
import remainder.api.rowmapper.EventRowMapper;

public class EventDaoImpl implements EventDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Event> getEventDetails(int eventId) {

		// TODO Auto-generated method stub
		String SQL = "select * from events where eventId = ?";
		List<Event> eventDetail = jdbcTemplateObject.query(SQL,
				new Object[] { eventId }, new EventRowMapper());
		return eventDetail;

	}

	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		String SQL = "select * from events";
		List<Event> eventDetail = jdbcTemplateObject.query(SQL,
				new Object[] {}, new EventRowMapper());
		return eventDetail;

	}

	public int insertEvent(Event event) {
		// TODO Auto-generated method stub

		int eventId, count = 0;
		String SQL = "", eventName = "";
		Random randomEventId;
		randomEventId = new Random();
		List<Event> eventDetail = null;
		while (true) {
			eventId = randomEventId.nextInt(1000);

			try {

				SQL = "select * from events where eventId = ?";
				eventDetail = jdbcTemplateObject.query(SQL,
						new Object[] { eventId }, new EventRowMapper());
				if (eventDetail.isEmpty()) {
					event.setEventId(eventId);
					break;
				} else
					eventDetail.clear();

			} catch (Exception e) {
				eventDetail.clear();
			}

		}
		System.out.println(event.getEventId());

		SQL = "insert into events values(" + event.getEventId() + ",'"
				+ event.getEventOrganizer() + "','" + event.getEventName()
				+ "','" + event.getEventDescription() + "','"
				+ event.getEventStartDate() + "','" + event.getEventStartTime()
				+ "','"+event.getEventEndDate() +"','" + event.getEventEndTime() + "'"
						+ ",'" + event.getEventLocation() + "','"
				+ event.getParticipantTeam() + "')";

		return jdbcTemplateObject.update(SQL);

	}

	public int updateEvent(int eventId,Event event) {
		// TODO Auto-generated method stub
		String SQL = "";

		SQL = "update events set eventOrganizer='" + event.getEventOrganizer()
				+ "',eventName='" + event.getEventName()
				+ "',eventDescription='" + event.getEventDescription()
				+ "',eventStartDate='" + event.getEventStartDate() + "',eventStartTime='"
				+ event.getEventStartTime() + "',eventEndDate='"
				+ event.getEventEndDate() + "',eventEndTime='"
				+ event.getEventEndTime() + "',eventLocation='"
				+ event.getEventLocation() + "',participantTeam='"
				+ event.getParticipantTeam() + "' where eventId="+eventId;

		return jdbcTemplateObject.update(SQL);

	}

	public int deleteEvent(int eventId) {
		// TODO Auto-generated method stub

		String SQL = "delete from events where eventId = ?";
		return jdbcTemplateObject.update(SQL, new Object[] { eventId });

	}

}
