import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializedTest {
	/*
	 * Base URL: https://rahulshettyacademy.com 
	 * Resource: /maps/api/place/add/json
	 * Query Parameters: key =qaclick123
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//To add value of Json playload
		AddPlace add = new AddPlace();
		add.setAccuracy(50);
		add.setAddress("302, Pleasant Park, Mira Road");
		add.setLanguage("Hindi-IN");
		add.setName("Utkarsh Singh");
		add.setPhone_number("9874563210");
		add.setWebsite("http://google.com");
		
		List<String> types_value = new ArrayList<String>();
		types_value.add("shoe park");
		types_value.add("shop");
		add.setTypes(types_value);
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		add.setLocation(loc);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String respond = given().log().all().queryParam("key", "qaclick123").header("Content-type","application/json")
		.body(add)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(respond);
	}

}
