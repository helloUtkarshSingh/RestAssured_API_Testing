import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import io.github.bonigarcia.wdm.WebDriverManager;

//4%2F0AWgavdeUICNj7r0C5eO255dQAcaXiqZ9ws_Ho-Ois1w2p5ccEyfmRNZNiOA1wPuA1tIzCg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent

public class Deserialize {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		WebDriver driver;
//		WebDriverManager.chromedriver().setup();
//		driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//		driver.get(
//				"https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&auth_url=https%3A%2F%2Faccounts.google.com%2Fo%2Foauth2%2Fv2%2Fauth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&state=verifyfjdss&&service=lso&o2v=2&flowName=GeneralOAuthFlow");
//		driver.findElement(By.xpath("//input[contains(@type,'email')]")).sendKeys("singhutkarsh1619@gmail.com");
//		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
//		
//		//driver.close();
		String[] course_Title = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWgavddVd9-BFcjozqHMvDJFhhauvwzb5bc_E3b7-1gacUBMSU4LZsyw_KGuE7CJaOOmuQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";

		String partial_code = url.split("code=")[1];
		String actual_code = partial_code.split("&scope")[0];

		System.out.println("partial Code:- " + partial_code);
		System.out.println("Actual Code:- " + actual_code);

		String accessToken_respond = given().urlEncodingEnabled(false).log().all().queryParam("code", actual_code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println(accessToken_respond);
		JsonPath js = new JsonPath(accessToken_respond);
		String accessToken = js.getString("access_token");

		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		ArrayList<String> actual_course = new ArrayList<String>();

		List<Api> total_course = gc.getCourses().getApi();
		for (int i = 0; i < total_course.size(); i++) {
			if (total_course.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(total_course.get(i).getPrice());
			}
		}

		List<WebAutomation> automation_course = gc.getCourses().getWebAutomation();
		for (int i = 0; i < automation_course.size(); i++) {
			System.out.println(automation_course.get(i).getCourseTitle());
			actual_course.add(automation_course.get(i).getCourseTitle());
		}
		// System.out.println(respond);
		List<String> expected_course = Arrays.asList(course_Title);
		Assert.assertTrue(expected_course.equals(actual_course));

	}

}
