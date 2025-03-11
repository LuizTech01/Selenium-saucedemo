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

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes de login automatizados")
public class LoginTests {

    @Test
    @DisplayName("Logar com sucesso")
    public void testLogarComSucesso() {
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
    @DisplayName("Exibir que o usuario esta bloqueado")
    public void testExibirQueOUsuarioEstaBloqueado() {
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
    @DisplayName("Logar com usuario com problema")
    public void testLogarComOUsuarioComProblema() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("problem_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        navegador.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement imgElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_item_img")));

        // Obtém o src da imagem
        String imgSrc = imgElement.getAttribute("src");
        System.out.println("🔎 Verificando imagem com src: " + imgSrc);

        if (isImageBroken(imgSrc)) {
            System.out.println("❌ A imagem NÃO carregou corretamente (404 Not Found).");
        } else {
            System.out.println("✅ A imagem carregou corretamente.");
        }

        navegador.quit();
    }

    @Test
    @DisplayName("Logar com usuario com problema de desempenho")
    public void testLogarComOUsuarioComProblemaDeDesempenho() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        navegador.findElement(By.id("password")).sendKeys("secret_sauce");
        long startTime = System.currentTimeMillis();
        navegador.findElement(By.id("login-button")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inventory_container")));

        long endTime = System.currentTimeMillis();

        long tempoTotal = endTime - startTime;
        System.out.println("⏳ Tempo de login: " + tempoTotal + " ms");

        if (tempoTotal > 3000) {
            System.out.println("⚠️ ALERTA: O login demorou mais do que o esperado!");
        } else {
            System.out.println("✅ O login foi realizado dentro do tempo aceitável.");
        }

        navegador.quit();
    }

    @Test
    @DisplayName("Tentando logar sem informar usuario e senha")
    public void testTentandoLogarSemInformarUsuarioESenha() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("login-button")).click();

        WebElement errorMessage = navegador.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
        String actualText = errorMessage.getText();
        assertTrue(actualText.contains("Epic sadface: Username is required"));

        navegador.quit();
    }

    @Test
    @DisplayName("Tentando logar sem senha")
    public void testTentandoLogarSemInformarSenha() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/index.html");

        navegador.findElement(By.id("user-name")).sendKeys("standard_user");
        navegador.findElement(By.id("login-button")).click();

        WebElement errorMessage = navegador.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
        String actualText = errorMessage.getText();
        assertTrue(actualText.contains("Epic sadface: Password is required"));

        navegador.quit();
    }

    public static boolean isImageBroken(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode != 200;
        } catch (Exception e) {
            return true;
        }
    }
}

