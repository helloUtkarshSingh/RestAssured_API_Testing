import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.AddProduct;
import pojo.GetLoginDetail;
import pojo.LoginResponse;
import pojo.OrderDetail;
import pojo.Orders;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcommerenceAPItest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Login Code
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		GetLoginDetail detail = new GetLoginDetail();
		detail.setUserEmail("Raju@gmail.com");
		detail.setUserPassword("Raju@12345");

		RequestSpecification Respreq = given().log().all().spec(req).body(detail);
		LoginResponse loginrespond = Respreq.when().post("/api/ecom/auth/login").then().log().all().extract().response()
				.as(LoginResponse.class);

		String token = loginrespond.getToken();
		String userId = loginrespond.getUserId();
		System.out.println(loginrespond.getToken());

		// Add product

		RequestSpecification addproductBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification addproductActualreq = given().log().all().spec(addproductBasereq)
				.param("productName", "Car").param("productAddedBy", userId).param("productCategory", "Electronic")
				.param("productSubCategory", "Office").param("productPrice", "11500")
				.param("productDescription", "Apple").param("productFor", "Both")
				.multiPart("productImage", new File("C:\\Users\\vi\\Pictures\\Camera Roll\\car.jpg"));

		String addproduct = addproductActualreq.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().asString();

		JsonPath Js = new JsonPath(addproduct);

		String productId = Js.get("productId");
		System.out.println(productId);

		// Place an Order

		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);

		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		orderDetailList.add(orderDetail);

		Orders orders = new Orders();
		orders.setOrders(orderDetailList);

		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();

		System.out.println(responseAddOrder);

	}

}
