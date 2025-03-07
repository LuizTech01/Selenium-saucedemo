package saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes de login automatizados")
public class LoginTests {

    @Test
    @DisplayName("Deve logar com sucesso")
    public void testDeveLogarComSucesso() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("standard_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();

        String currentUrl = navegador.getCurrentUrl();
        String expectedUrl = "https://www.saucedemo.com/v1/inventory.html";
        assertEquals(expectedUrl, currentUrl, "A URL não foi redirecionada corretamente!");

        navegador.quit();
    }

    @Test
    @DisplayName("Deve exibir que o usuario esta bloqueado")
    public void testDeveExibirQueOUsuarioEstaBloqueado() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("locked_out_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();

        WebElement errorMessage = navegador.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
        String actualText = errorMessage.getText();
        assertTrue(actualText.contains("Epic sadface: Sorry, this user has been locked out."));

        navegador.quit();
    }

    @Test
    @DisplayName("Deve logar mas o usuario tem problema")
    public void testDeveLogarMasOUsuarioTemProblema() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("problem_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();

        String currentUrl = navegador.getCurrentUrl();
        String expectedUrl = "https://www.saucedemo.com/v1/inventory.html";
        assertEquals(expectedUrl, currentUrl, "A URL não foi redirecionada corretamente!");

        navegador.quit();
    }

    @Test
    @DisplayName("Deve logar mas o usuario tem problema de desempenho")
    public void testDeveLogarMasOUsuarioTemProblemaDeDesempenho() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();

        navegador.quit();
    }

}
