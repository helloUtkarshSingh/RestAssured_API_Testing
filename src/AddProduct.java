import io.restassured.RestAssured;
import pojo.GetLoginDetail;
import pojo.LoginResponse;

import static io.restassured.RestAssured.*;

import java.io.File;

public class AddProduct {
	public static void main(String[] args) {
		
		GetLoginDetail detail = new GetLoginDetail();
		detail.setUserEmail("Raju@gmail.com"); 
		detail.setUserPassword("Raju@12345");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
		
		//login
		LoginResponse loginrespond = given().log().all().header("Content-Type", "application/json").body(detail).when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		String token = loginrespond.getToken();
		String userId = loginrespond.getUserId();
		System.out.println(loginrespond.getToken());
		
		//Add product
		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
		String A = given().log().all().header("Authorization", token).param("productName", "Laptop").param("productAddedBy", userId).param("productCategory", "Electronic").param("productSubCategory", "Office").param("productPrice", "11500").param("productDescription", "Apple").param("productFor", "Both").multiPart("productImage",new File("C:\\Users\\vi\\Pictures\\Camera Roll\\Ps.jpg"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		System.out.println(A);
	}

}
