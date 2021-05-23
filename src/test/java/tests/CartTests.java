package tests;

import static com.codeborne.selenide.Selenide.refresh;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartTests extends TestBase {

  private final ServiceUI serviceUI = new ServiceUI();
  private final ServiceAPI serviceAPI = new ServiceAPI();

  @Test
  @DisplayName("Проверка счетчика корзины не авторизованного по API пользователя после добавления товара в корзину по API")
  void addItemToCartAsNewUserTest() {
    String cookies = serviceAPI.getCookieNewUser();
    serviceAPI.addToCartWithCheckResult(cookies, 1);
  }

  @Test
  @DisplayName("Проверка счетчика корзины авторизованного по API пользователя после добавления товара в корзину по API")
  void checkCartCounterAuthUserAPITest() {
    String cookies = serviceAPI.getCookieAfterAPIAuth();
    int result = serviceAPI.getCurrentValueInCartAPI(cookies) + 1;
    serviceAPI.addToCartWithCheckResult(cookies, result);
  }

  @Test
  @DisplayName("Проверка счетчика корзины авторизованного по API пользователя после добавления товара(2 шт.) в корзину по API")
  void checkCartCounterAuthUserDoubleItemTest() {
    String cookies = serviceAPI.getCookieAfterAPIAuth();
    int currentCount = serviceAPI.getCurrentValueInCartAPI(cookies);
    serviceAPI.addToCartConfigured(cookies, currentCount, 31, 2);
  }

  @Test
  @DisplayName("Проверка в UI счетчика корзины авторизованного пользователя после добавления товара в корзину по API")
  void checkUICartCounterAfterAPIRequestTest() {
    String cookies = serviceUI.getCookieAfterWebAuth();
    int result = serviceUI.getCurrentValueInCartUI() + 1;
    serviceAPI.addToCart(cookies);
    refresh();
    serviceUI.checkCartCounter(result);
  }

  @Test
  @DisplayName("Проверка счетчика корзины авторизованного пользователя после добавления товара в корзину по UI")
  void checkCartCounterUITest() {
    serviceUI.authorizationUI();
    int result = serviceUI.getCurrentValueInCartUI() + 1;
    serviceUI.addToCart("build-your-own-expensive-computer-2");
    serviceUI.checkCartCounter(result);
  }

}
