package remainder.api.bean;

import io.swagger.annotations.ApiModelProperty;

public class Event
{
	@ApiModelProperty(position = 1, required = true, value = "testId containing only lowercase letters or numbers")
	private String eventOrganizer;
	@ApiModelProperty(position = 2, required = true, value = "testId containing only lowercase letters or numbers")
	private String eventDescription;
	@ApiModelProperty(position = 3, required = false, value = "Test created timestamp is added by the system")
	private String eventStartTime;
	@ApiModelProperty(position = 4, required = true, value = "Test Duration containing numbers in mins")
	private String eventStartDate;
	@ApiModelProperty(position = 5, required = true, value = "Test Duration containing numbers in mins")
	private String eventEndDate;
	@ApiModelProperty(position = 6, required = true, value = "Test Duration containing numbers in mins")
	private String participantTeam;
	@ApiModelProperty(position = 7, required = true, value = "Test Duration containing numbers in mins")
	private String eventLocation;
	@ApiModelProperty(position = 8, required = true, value = "testId containing only lowercase letters or numbers")
	private String eventName;
	@ApiModelProperty(position = 9, required = true, value = "testId containing only lowercase letters or numbers")
	private int eventId;
	@ApiModelProperty(position = 10, required = true, value = "testId containing only lowercase letters or numbers")
	private String eventEndTime;
	
	public String getEventOrganizer() {
		return eventOrganizer;
	}
	public void setEventOrganizer(String eventOrganizer) {
		this.eventOrganizer = eventOrganizer;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getEventStartTime() {
		return eventStartTime;
	}
	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}
	public String getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(String eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public String getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(String eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public String getParticipantTeam() {
		return participantTeam;
	}
	public void setParticipantTeam(String participantTeam) {
		this.participantTeam = participantTeam;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventEndTime() {
		return eventEndTime;
	}
	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}
	
		
}
