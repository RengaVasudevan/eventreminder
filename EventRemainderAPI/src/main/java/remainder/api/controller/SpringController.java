package remainder.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import remainder.api.bean.Event;
import remainder.api.daoimpl.EventDaoImpl;
import remainder.api.exception.EventApiException;
import remainder.api.model.Data;
import remainder.api.model.ErrorDetails;
import remainder.api.model.MetaData;
import remainder.api.model.Response;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@Api(value = "Events", description = "Event Reminder")
public class SpringController {
	@Autowired
	EventDaoImpl remainderJDBCTemplate;
	@Autowired
	MetaData metaData;

	@Autowired
	Data data;
	@Autowired
	Response response;
	@Autowired
	ErrorDetails errorDetails;

	List<String> noData =new ArrayList();
	     
	// gets_event_details
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Returns event details", notes = "Returns the details of a event", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Response.class),
			@ApiResponse(code = 201, message = "Created", response = Response.class),
			@ApiResponse(code = 404, message = "Event with given ID does not exist", response = Response.class),
			@ApiResponse(code = 400, message = "Event with given id does not exist", response = Response.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Response.class) })
	@ApiParam(name = "eventId", value = "eventId", required = true)
	@RequestMapping(value = "/events/{eventId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> getEventDetails(
			@ApiParam(name = "eventid", value = "eventid", required = true) @PathVariable("eventId") int eventId)
			throws Exception {
		try {
			List<Event> eventDetails = remainderJDBCTemplate
					.getEventDetails(eventId);
			if (!(eventDetails.isEmpty())) {
				saveMetaData(true, "Event detail loaded", "12345");
				saveData(null, eventDetails);
				errorDetails.setCode("no error message");
				errorDetails
						.setDescription("no error message");
				saveResponse(data, metaData, errorDetails);
			}

			else {
				 noData.add("no output data");
				 saveData(null,noData);
				saveMetaData(false, "Error Occured", "respId12345");
				
				errorDetails.setCode("00005");
				errorDetails
						.setDescription("Event has got over or Event with ID "
								+ eventId + " does not exist !");
				saveResponse(data, metaData, errorDetails);

				throw new EventApiException(
						"Event has got over or Event with ID " + eventId
								+ " does not exist !");
			}
		} catch (EventApiException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(response,
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorDetails.setCode("00005");

			if (e instanceof IncorrectResultSizeDataAccessException
					|| e instanceof EmptyResultDataAccessException)
				errorDetails.setDescription("Bad Request");
			else if (e instanceof DataAccessException)
				errorDetails.setDescription("Database error");
			else
				errorDetails.setDescription(e.getMessage());
			;
			saveMetaData(false, "Error Occured", "respId12345");
			// saveData(errorDetails, null);
			saveResponse(null, metaData, errorDetails);
			// return response;

		}

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	// gets_allevent_details
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Returns event details", notes = "Returns the details of a event with ID", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK", response = Response.class),
			@ApiResponse(code = 201, message = "Created", response = Response.class),
			@ApiResponse(code = 404, message = "Customer with given ID does not exist", response = Response.class),
			@ApiResponse(code = 400, message = "Customer with customerid does not exist", response = Response.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Response.class) })
	@RequestMapping(value = "/events", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> getAllEvents() throws Exception {

		List<Event> eventDetails = remainderJDBCTemplate.getAllEvents();
		try {
			if (!eventDetails.isEmpty()) {
				saveMetaData(true, "All Event detail loaded", "12345");
				saveData(null, eventDetails);
				errorDetails.setCode("no error message");
				errorDetails
						.setDescription("no error message");
				saveResponse(data, metaData, errorDetails);
			} else {
				saveMetaData(false, "Error Occured", "respId12345");
				 noData.add("no output data");
				 saveData(null,noData);
				errorDetails.setCode("00005");
				errorDetails
						.setDescription("Currently No Events or Programmes");
				saveResponse(null, metaData, errorDetails);

				throw new EventApiException("Currently No Events or Programmes");
			}
		} catch (EventApiException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(response,
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorDetails.setCode("00005");

			if (e instanceof IncorrectResultSizeDataAccessException
					|| e instanceof EmptyResultDataAccessException)
				errorDetails.setDescription("Bad Request");
			else if (e instanceof DataAccessException)
				errorDetails.setDescription("Database error");
			else
				errorDetails.setDescription(e.getMessage());
			;
			saveMetaData(false, "Error Occured", "respId12345");
			// saveData(errorDetails, null);
			saveResponse(null, metaData, errorDetails);
			// return response;

		}

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiParam(name = "event object", value = "event object", required = true)
	@ApiOperation(value = "Adds an event", notes = "Adds event with its details", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Event inserted", response = Response.class),
			@ApiResponse(code = 404, message = "Event with given ID exists already", response = Response.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Response.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Response.class) })
	@RequestMapping(value = "/events", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> insertCustomer(@RequestBody Event event) {
		List<Event> eventDetails = new ArrayList<Event>();

		try {

			int a = remainderJDBCTemplate.insertEvent(event);
			System.out.println("Insertion exceution status");
			System.out.println(a);
			if (a == 1) {
				eventDetails.add(event);
				saveMetaData(true, "Event Added", "14563");
				saveData(null, eventDetails);
				errorDetails.setCode("no error message");
				errorDetails
						.setDescription("no error message");
				saveResponse(data, metaData, errorDetails);
			} else {
				saveMetaData(false, "Error Occured", "respId12345");
				 noData.add("no output data");
				 saveData(null,noData);
				errorDetails.setCode("00005");
				errorDetails.setDescription("Invalid insertion data !");
				saveResponse(null, metaData, errorDetails);

				throw new EventApiException("Insertion falied");
			}

		} catch (EventApiException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(response,
					HttpStatus.BAD_REQUEST);
		}

		catch (Exception e) {
			e.printStackTrace();
			errorDetails.setCode("00005");
			if (e instanceof DataAccessException) {
				errorDetails.setDescription("Database Error");
			} else {
				errorDetails.setDescription(e.getMessage());
			}
			saveMetaData(false, "Error Occured", "14563");
			// saveData(errorDetails, null);
			saveResponse(null, metaData, errorDetails);
			return new ResponseEntity<Response>(response, HttpStatus.OK);

		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiParam(name = "event object", value = "Event object", required = true)
	@ApiOperation(value = "Updates a event", notes = "updates event details", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful updation of customer", response = Response.class),
			@ApiResponse(code = 404, message = "Updation failure", response = Response.class),
			@ApiResponse(code = 400, message = "test with testid does not exist", response = Response.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Response.class) })
	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> updateCustomer(
			@RequestBody Event updatedEventDetails,
			@ApiParam(name = "eventId", value = "eventId", required = true) @PathVariable("eventId") int eventId) {
		List<Event> eventDetails = new ArrayList<Event>();

		try {
			int a = remainderJDBCTemplate.updateEvent(eventId,updatedEventDetails);
			System.out.println("Updation exceution status");
			System.out.println(a);
			if (a == 1) {
				eventDetails.add(updatedEventDetails);
				saveMetaData(true, "Event details Updated", "123457");
				saveData(null, eventDetails);
				errorDetails.setCode("no error message");
				errorDetails
						.setDescription("no error message");
				saveResponse(data, metaData, errorDetails);
			} else {
				saveMetaData(false, "Error Occured", "respId12345");
				 noData.add("no output data");
				 saveData(null,noData);
				errorDetails.setCode("00005");
				errorDetails.setDescription("Invalid updation data !");
				saveResponse(null, metaData, errorDetails);

				throw new EventApiException("Updation falied");

			}

		} catch (EventApiException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(response,
					HttpStatus.BAD_REQUEST);
		}

		catch (Exception e) {
			e.printStackTrace();
			errorDetails.setCode("00005");
			errorDetails.setDescription(e.getMessage());
			;
			saveMetaData(false, "Error Occured", "123457");
			// saveData(errorDetails, null);
			saveResponse(null, metaData, errorDetails);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@ApiOperation(value = "Removes an event", notes = " Deletes a event from database", response = Response.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful Deletion of event", response = Response.class),
			@ApiResponse(code = 400, message = "event does not exist", response = Response.class),
			@ApiResponse(code = 404, message = "Deletion failure"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@RequestMapping(value = "/events/{eventId}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Response> deleteCustomer(
			@ApiParam(name = "eventId", value = "eventId", required = true) @PathVariable("eventId") int eventId) {
		List<Event> eventDetails = new ArrayList<Event>();

		try {
			eventDetails = remainderJDBCTemplate.getEventDetails(eventId);

			if (!(eventDetails.isEmpty())) {
				int a = remainderJDBCTemplate.deleteEvent(eventId);
				System.out.println("Deletion exceution status");
				System.out.println(a);
				saveMetaData(true, "Event Deleted", "24541");
				saveData(null, eventDetails);
				errorDetails.setCode("no error message");
				errorDetails
						.setDescription("no error message");
				saveResponse(data, metaData, errorDetails);
			} else {
				 noData.add("no output data");
				 saveData(null,noData);
				saveMetaData(false, "Error Occured", "respId12345");

				errorDetails.setCode("00005");
				errorDetails.setDescription("Customer Id does not exist!");
				saveResponse(null, metaData, errorDetails);

				throw new EventApiException("Deletion failed");

			}

		} catch (EventApiException e) {
			e.printStackTrace();
			return new ResponseEntity<Response>(response,
					HttpStatus.BAD_REQUEST);
		}

		catch (Exception e) {
			e.printStackTrace();
			errorDetails.setCode("00005");
			errorDetails.setDescription(e.getMessage());
			;
			saveMetaData(false, "Error Occured", "24541");
			saveData(errorDetails, null);
			saveResponse(null, metaData, errorDetails);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param data
	 *            Data
	 * @param metaData
	 *            MetaData
	 * @param errorDet
	 *            errorDet
	 */
	private void saveResponse(Data data, MetaData metaData,
			ErrorDetails errorDet) {
		response.setData(data);
		response.setMetaData(metaData);
		response.setError(errorDet);
	}

	private void saveData(ErrorDetails erroDet, List testObj) {
		response.setError(erroDet);
		data.setOutput(testObj);

	}

	private void saveMetaData(boolean success, String description,
			String responseId) {

		metaData.setSuccess(success);
		metaData.setDescription(description);
		metaData.setResponseId(responseId);
	}
}
