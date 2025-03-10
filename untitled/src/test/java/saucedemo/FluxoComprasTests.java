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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FluxoComprasTests {

    @Test
    @DisplayName("Comprando produto")
    public void testComprandoProduto() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_button")).click();
        navegador.findElement(By.className("cart_button")).click();

        WebElement mensagemAgradecimento = navegador.findElement(By.id("checkout_complete_container"));
        String textoMensagem = mensagemAgradecimento.getText();
        assertTrue(textoMensagem.contains("THANK YOU FOR YOUR ORDER"), "A mensagem de agradecimento não está visível ou não está correta!");
        WebElement mensagemDetalhes = navegador.findElement(By.xpath("//div[contains(text(),'Your order has been dispatched')]"));
        String textoDetalhes = mensagemDetalhes.getText();
        assertTrue(textoDetalhes.contains("Your order has been dispatched"), "A segunda parte da mensagem não está visível ou não está correta!");

        navegador.quit();
    }

    @Test
    @DisplayName("Comprando mais produtos")
    public void testComprandoMaisProdutos() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        List<WebElement> produtosNoCarrinho = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("cart_item")));

        String primeiroProduto = produtosNoCarrinho.get(0).getText();
        assertTrue(primeiroProduto.contains("Sauce Labs Backpack"), "O nome do primeiro produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[2]/a[1]")).click();

        List<WebElement> botao= navegador.findElements(By.className("btn_primary"));
        botao.get(0).click();

        navegador.findElement(By.id("shopping_cart_container")).click();

        List<WebElement> produtosAtualizados = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("cart_item")));
        String segundoProduto = produtosAtualizados.get(1).getText();
        assertTrue(segundoProduto.contains("Sauce Labs Bike Light"), "O nome do segundo produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_button")).click();
        navegador.findElement(By.className("cart_button")).click();

        WebElement mensagemAgradecimento = navegador.findElement(By.id("checkout_complete_container"));
        String textoMensagem = mensagemAgradecimento.getText();
        assertTrue(textoMensagem.contains("THANK YOU FOR YOUR ORDER"), "A mensagem de agradecimento não está visível ou não está correta!");
        WebElement mensagemDetalhes = navegador.findElement(By.xpath("//div[contains(text(),'Your order has been dispatched')]"));
        String textoDetalhes = mensagemDetalhes.getText();
        assertTrue(textoDetalhes.contains("Your order has been dispatched"), "A segunda parte da mensagem não está visível ou não está correta!");

        navegador.quit();
    }

    @Test
    @DisplayName("Removendo produto do carrinho")
    public void testRemovendoProdutoDoCarrinho() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("btn_secondary")).click();

        boolean produtoDesapareceu = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("cart_item")));
        assertTrue(produtoDesapareceu, "O produto ainda está no carrinho!");

        navegador.quit();
    }

    @Test
    @DisplayName("Nao informa primeiro nome")
    public void testNaoInformaPrimeiroNome() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_button")).click();

        WebDriverWait wait2 = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement mensagemErro = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        String textoErro = mensagemErro.getText();
        assertTrue(textoErro.contains("Error: First Name is required"), "A mensagem de erro não está visível ou não contém o texto esperado!");

        navegador.quit();
    }

    @Test
    @DisplayName("Nao informa ultimo nome")
    public void testNaoInformaUltimoNome() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_button")).click();

        WebDriverWait wait2 = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement mensagemErro = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        String textoErro = mensagemErro.getText();
        assertTrue(textoErro.contains("Error: Last Name is required"), "A mensagem de erro não está visível ou não contém o texto esperado!");

        navegador.quit();
    }

    @Test
    @DisplayName("Nao informa zip postal code")
    public void testNaoInformaZipPostalCode() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.className("cart_button")).click();

        WebDriverWait wait2 = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement mensagemErro = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));
        String textoErro = mensagemErro.getText();
        assertTrue(textoErro.contains("Error: Postal Code is required"), "A mensagem de erro não está visível ou não contém o texto esperado!");

        navegador.quit();
    }

    @Test
    @DisplayName("Cancelar compra na pagina de checkout")
    public void testCancelarCompraNaPaginaDeCheckout() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_cancel_link")).click();

        WebDriverWait wait2 = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho2 = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));
        String nomeProduto2 = produtoNoCarrinho2.getText();
        assertTrue(nomeProduto2.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.quit();
    }

    @Test
    @DisplayName("Cancelando compra na tela de checkout overview")
    public void testCancelandoCompraNaTelaDeCheckoutOverview() {
        WebDriverManager.chromedriver().setup();
        WebDriver navegador = new ChromeDriver();

        navegador.get("https://www.saucedemo.com/v1/inventory.html");

        navegador.findElement(By.className("btn_primary")).click();
        navegador.findElement(By.id("shopping_cart_container")).click();

        WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(10));
        WebElement produtoNoCarrinho = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart_item")));

        String nomeProduto = produtoNoCarrinho.getText();
        assertTrue(nomeProduto.contains("Sauce Labs Backpack"), "O nome do produto no carrinho não está correto ou não está visível!");

        navegador.findElement(By.className("checkout_button")).click();

        navegador.findElement(By.id("first-name")).sendKeys("Teste");
        navegador.findElement(By.id("last-name")).sendKeys("Silva");
        navegador.findElement(By.id("postal-code")).sendKeys("11111111");
        navegador.findElement(By.className("cart_button")).click();
        navegador.findElement(By.className("cart_cancel_link")).click();

        String currentUrl = navegador.getCurrentUrl();
        String expectedUrl = "https://www.saucedemo.com/v1/inventory.html";
        assertEquals(expectedUrl, currentUrl, "A URL não foi redirecionada corretamente!");

        navegador.quit();
    }

}
