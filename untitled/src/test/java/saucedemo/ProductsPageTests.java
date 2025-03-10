package saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsPageTests {

    @Test
    @DisplayName("Acessando a pagina de produtos com sucesso")
    public void testAcessandoAPaginaDeProdutosComSucesso() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.quit();
    }

    @Test
    @DisplayName("Mudando a ordem dos produtos")
    public void testMudandoAOrdemDosProdutos() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("product_sort_container")).click();

        WebElement selectElement = navegador.findElement(By.className("product_sort_container"));
        Select select = new Select(selectElement);
        select.selectByValue("za");

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement primeiroProduto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        String nomeProduto = primeiroProduto.getText();
        assertEquals("Test.allTheThings() T-Shirt (Red)", nomeProduto, "O nome do primeiro item não está correto!");

        navegador.quit();
    }

    @Test
    @DisplayName("Mudando a ordem dos produtos por de menor a maior")
    public void testMudandoAOrdemDosProdutosPorDeMenorAMaior() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("product_sort_container")).click();

        WebElement selectElement = navegador.findElement(By.className("product_sort_container"));
        Select select = new Select(selectElement);
        select.selectByValue("lohi");

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement primeiroProduto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        String nomeProduto = primeiroProduto.getText();
        assertEquals("Sauce Labs Onesie", nomeProduto, "O nome do primeiro item não está correto!");

        navegador.quit();
    }

    @Test
    @DisplayName("Mudando a ordem dos produtos por de maior a menor")
    public void testMudandoAOrdemDosProdutosPorDeMaiorAMenor() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("product_sort_container")).click();

        WebElement selectElement = navegador.findElement(By.className("product_sort_container"));
        Select select = new Select(selectElement);
        select.selectByValue("hilo");

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement primeiroProduto = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        String nomeProduto = primeiroProduto.getText();
        assertEquals("Sauce Labs Fleece Jacket", nomeProduto, "O nome do primeiro item não está correto!");

        navegador.quit();
    }

    @Test
    @DisplayName("Adicionando produtos ao carrinho")
    public void testAdicionandoProdutosAoCarrinho() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.quit();
    }

}
