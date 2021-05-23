package tests;

import static com.codeborne.selenide.Selenide.closeWebDriver;

import org.junit.jupiter.api.AfterEach;

public class TestBase {

  public static String BASE_URL = "http://demowebshop.tricentis.com/";

  @AfterEach
  void afterTest() {
    closeWebDriver();
  }
}
