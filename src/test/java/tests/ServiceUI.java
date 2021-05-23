package tests;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static tests.TestBase.BASE_URL;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUI {

  void authorizationUI() {
    Selenide.open(BASE_URL);
    $(".header-links .ico-login").click();
    $("#Email").setValue("qw123@qwer.ty");
    $("#Password").setValue("123456");
    $(".login-button").click();
  }

  String getCookieAfterWebAuth() {
    authorizationUI();
    return WebDriverRunner.getWebDriver().manage().getCookieNamed("NOPCOMMERCE.AUTH").getValue();
  }

  void checkCartCounter(int count) {
    $("#topcartlink .cart-qty").shouldHave(Condition.exactText("(" + count + ")"));
  }

  int getCurrentValueInCartUI() {
    Pattern pattern = Pattern.compile("(\\d{1,})");
    String textFromPage = $("#topcartlink .cart-qty").getText();
    Matcher matcher = pattern.matcher(textFromPage);
    int s = 0;
    if (matcher.find()) {
      s = Integer.parseInt(matcher.group(1));
    }
    return s;
  }

  void addToCart(String item) {
    openItemPage(item);
    $("#add-to-cart-button-74").click();
  }

  void openItemPage(String item) {
    open(BASE_URL + item);
  }
}