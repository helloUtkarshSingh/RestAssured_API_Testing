
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import file.ReuseableMethod;
import file.payload;

public class Basic {

	public static void main(String[] args) throws IOException {
		// validate if ADD place API is working as expected
		/*
		 * given: all the input details i.e (params,header,body) 
		 * when: submit the APIi.e(remaining part of the url) 
		 * then: validate the response(statuscode)
		 * To use JSon as external file. 1) convert the JSon to Byte. So, Use the following code given below.
		 *  new String (Files.readAllBytes(Paths.get("C:\\Users\\vi\\payload.json")))
		 */

		System.out.println("*******************POST*****************");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String respond = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String (Files.readAllBytes(Paths.get("C:\\Users\\vi\\payload.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();
		// To extract the output use :- extract().response().asString()
		// to convert String to Json
		JsonPath js = new JsonPath(respond);
		String placeID = js.getString("place_id");
		System.out.println("************************************");
		System.out.println("placeId:-" + placeID);
		System.out.println("************************************");

		System.out.println("**************// UPDATE**********************");
		String newAddress  = "Vivekpur, USA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\""+placeID+"\",\r\n"
						+ "\"address\":\""+newAddress+"\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).extract().response().asString();
		System.out.println("************************************");
		
		
		System.out.println("****************GET********************");
	   String Get_respond =  given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
	    .when().get("maps/api/place/get/json")
	    .then().assertThat().log().all().statusCode(200).extract().response().asString();
	   JsonPath js1 = ReuseableMethod.StringToJson(Get_respond);
	   String Get_actual_Address = js1.getString("address");
	   
	   assertEquals(Get_actual_Address,newAddress);
	   
	System.out.println(Get_actual_Address);
	}

}
