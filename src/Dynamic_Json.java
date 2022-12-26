import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import file.ReuseableMethod;
import file.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Dynamic_Json {

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";

		String rspond = given().log().all().header("Content-Type", "application/json")
				.body(payload.AddBook(isbn, aisle))
				.when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReuseableMethod.StringToJson(rspond);
		String id = js.get("ID");
		System.out.println(id);
		
		System.out.println("Delete request");
		String respond1 = given().log().all().header("Content-Type", "application/json")
		.body(payload.DeleteBook(isbn, aisle))
		.when().post("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js1 = ReuseableMethod.StringToJson(respond1);
		String mss = js1.get("msg");
		System.out.println("The Printed message:-"+mss);

	}


	@DataProvider(name = "BookData")
	public Object[][] getdata() {

//		return new Object[][] { { "nvstdd", "50040" }, { "kgjsyr", "16037" }, { "shjhdk", "79767" } };
		return new Object[][] { { "Utkarsh", "69645869" } };
	}

}
