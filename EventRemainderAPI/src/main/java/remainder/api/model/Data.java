package remainder.api.model;

import io.swagger.annotations.ApiModelProperty;
import remainder.api.bean.*;
import java.util.List;

public class Data {
	
	@ApiModelProperty(position = 1, required = true, value = "brief description of the property :output ")
	private List<Event> output;
	
	public List<Event>  getOutput() {
		return output;
	}
	public void setOutput(List<Event> outputList) {
		this.output = outputList;
	}
	
	
}
