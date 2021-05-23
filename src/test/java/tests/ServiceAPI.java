package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static tests.TestBase.BASE_URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceAPI {

  int getCurrentValueInCartAPI(String cookies) {
    Pattern pattern = Pattern.compile("<span class=\"cart-qty\">\\((\\d{1,})\\)<\\/span>");
    String cartPage = given()
        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
        .cookie("NOPCOMMERCE.AUTH", cookies)
        .when()
        .get(BASE_URL + "cart")
        .then()
        .statusCode(200)
        .extract()
        .body().asString();

    Matcher matcher = pattern.matcher(cartPage);
    int s = 0;
    if (matcher.find()) {
      s = Integer.parseInt(matcher.group(1));
    }
    return s;
  }

  String getCookieAfterAPIAuth() {
    return given()
        .contentType("application/x-www-form-urlencoded")
        .formParam("Email", "qw123@qwer.ty")
        .formParam("Password", "123456")
        .when()
        .post("http://demowebshop.tricentis.com/login")
        .then()
        .statusCode(302)
        .extract()
        .response()
        .getCookie("NOPCOMMERCE.AUTH");
  }

  String getCookieNewUser() {
    return given()
        .contentType("application/x-www-form-urlencoded")
        .when()
        .post("http://demowebshop.tricentis.com/login")
        .then()
        .statusCode(200)
        .extract()
        .response()
        .getCookie("NOPCOMMERCE.AUTH");
  }

  void addToCart(String cookies) {
    given()
        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
        .body("addtocart_31.EnteredQuantity=1")
        .cookie("NOPCOMMERCE.AUTH", cookies)
        .when()
        .post(BASE_URL + "addproducttocart/details/31/1")
        .then()
        .statusCode(200)
        .body("success", is(true));
  }

  void addToCartConfigured(String cookies, int currentCounter, int itemNumber, int quantity) {
    int result = currentCounter + quantity;
    given()
        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
        .body("addtocart_" + itemNumber + ".EnteredQuantity=" + quantity)
        .cookie("NOPCOMMERCE.AUTH", cookies)
        .when()
        .post(BASE_URL + "addproducttocart/details/" + itemNumber + "/1")
        .then()
        .statusCode(200)
        .body("success", is(true))
        .body("updatetopcartsectionhtml", is("(" + result + ")"));
  }

  void addToCartWithCheckResult(String cookies, Integer result) {
    given()
        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
        .body("addtocart_31.EnteredQuantity=1")
        .cookie("NOPCOMMERCE.AUTH", cookies)
        .when()
        .post(BASE_URL + "addproducttocart/details/31/1")
        .then()
        .statusCode(200)
        .body("success", is(true))
        .body("updatetopcartsectionhtml", is("(" + result + ")"));
  }

  static void logout() {
    given()
        .when()
        .get(BASE_URL + "logout")
        .then()
        .statusCode(302);
  }
}