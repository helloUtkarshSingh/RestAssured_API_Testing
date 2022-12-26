package file;

import io.restassured.path.json.JsonPath;

public class ReuseableMethod {
	
	public static JsonPath StringToJson(String respond) {
		
		JsonPath js = new JsonPath(respond);
		return js;
		
	}
	
	

}
