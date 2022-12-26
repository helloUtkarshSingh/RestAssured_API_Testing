import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.AddProduct;
import pojo.GetLoginDetail;
import pojo.LoginResponse;
import pojo.Orderdetail;
import pojo.PlacingDetail;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcommerenceAPItest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Login Code
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
         
		GetLoginDetail detail = new GetLoginDetail();
		detail.setUserEmail("RahulSingh@gmail.com"); 
		detail.setUserPassword("Rahul@12345");

		RequestSpecification Respreq = given().log().all().spec(req).body(detail);
		LoginResponse loginrespond = Respreq.when().post("/api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginResponse.class);

		String token = loginrespond.getToken();
		String userId = loginrespond.getUserId();
		System.out.println(loginrespond.getToken());
		
		//Add product 
		RequestSpecification Addproductreq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification Addreq = given().log().all().spec(Addproductreq).param("productName", "PS5").param("productAddedBy", userId)
		.param("productCategory", "Game").param("productSubCategory", "VR game").param("productPrice", "11500")
		.param("productDescription", "Sony").param("productImage", "Men").multiPart("productImage",new File("C:\\Users\\vi\\Pictures\\Camera Roll\\car.jpg"));
		
		AddProduct addproduct = Addreq.when().post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201).extract().response().as(AddProduct.class);
		String product_id= addproduct.getProductId();
		System.out.println(product_id);
		
		//Place an Order
		RequestSpecification PlaceproductBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		Orderdetail orderdetail = new Orderdetail();
		orderdetail.setCountry("India");
		orderdetail.setProductOrderedId(product_id);
		List<Orderdetail> OrderdetaiList = new ArrayList<Orderdetail>();
		OrderdetaiList.add(orderdetail);
		
		PlacingDetail placingdetail = new PlacingDetail();
		placingdetail.setOrder(OrderdetaiList);
		
		RequestSpecification PlaceproductActualreq = given().log().all().spec(PlaceproductBasereq).body(placingdetail);
		
		String success = PlaceproductActualreq.when().post("/api/ecom/order/create-order")
		.then().log().all().assertThat().extract().response().asString();
		
//		String productID = success.getProductOrderId();
//		System.out.println(productID);
		
		}
	

}
