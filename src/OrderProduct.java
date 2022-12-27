import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.GetLoginDetail;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OrderProduct {
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
		System.out.println(loginrespond.getUserId());
		
		//Add product
		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
		String A = given().log().all().header("Authorization", token).param("productName", "Laptop").param("productAddedBy", userId).param("productCategory", "Electronic").param("productSubCategory", "Office").param("productPrice", "11500").param("productDescription", "Apple").param("productFor", "Both").multiPart("productImage",new File("C:\\Users\\vi\\Pictures\\Camera Roll\\Ps.jpg"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath  jp= new JsonPath(A);
		String productId = jp.get(userId);
		
		//Place order
		OrderDetail orderdetail = new OrderDetail();
		orderdetail.setCountry("India");
		orderdetail.setProductOrderedId(productId);
		List<OrderDetail> OrderdetaiList = new ArrayList<OrderDetail>();
		OrderdetaiList.add(orderdetail);

		Orders placingdetail = new Orders();
		placingdetail.setOrder(OrderdetaiList);
		
//		RestAssured.baseURI = "https://rahulshettyacademy.com"; 
//		String B = given().log().all().header("Authorization", token)
//		.when().post("/api/ecom/product/add-product").body().then().log().all().extract().response().asString();
		
	}

}
