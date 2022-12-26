import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

//4%2F0AWgavdeUICNj7r0C5eO255dQAcaXiqZ9ws_Ho-Ois1w2p5ccEyfmRNZNiOA1wPuA1tIzCg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent

public class oAuthTest {

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
		
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWgavdcQ_CGhTfpmAL7ZKmb1Jt0loPP-HDRj2V0vQ6OEX69RvHp6XQ0fOr3qa6A0f_2eRA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		
		String partial_code = url.split("code=")[1];
		String actual_code = partial_code.split("&scope")[0];
		
		System.out.println("partial Code:- "+partial_code);
		System.out.println("Actual Code:- "+actual_code);
		

		String accessToken_respond = given().urlEncodingEnabled(false)
				.log().all().queryParam("code", actual_code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println(accessToken_respond);
		JsonPath js = new JsonPath(accessToken_respond);
		String accessToken = js.getString("access_token");

		String respond = given().log().all().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();

		System.out.println(respond);

	}

}
