CREATE TABLE events
(
 eventId int primary key not null,eventOrganizer varchar(40) not null,eventName varchar(40) not null,eventDescription clob,eventStartDate varchar(15),eventStartTime varchar(10),eventEndDate varchar(15),eventEndTime varchar(10),eventLocation varchar(25),
 participantTeam varchar(25)
   
);

/*drop table events*/

create schema ren_schema;
select * from events;
desc events;

insert into events values(123,'Suriya','Business communication','Improves Your Business communciation','05-01-2018','04:00 PM','05-01-2018','05:00 PM','Takshila','Copernicus');
970 Vijay          Presentation and communication class Improves Your Presentation and communciation skills 21-02-2017 02:00 PM 22-02-2017 03:00 PM     Tuesday  Nalanda       daVinci
