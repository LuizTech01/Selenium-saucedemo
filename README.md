# Selenium-saucedemo

Este projeto realiza testes automatizados no site [SauceDemo](https://www.saucedemo.com/) utilizando Selenium WebDriver com Java.  

## Tecnologias utilizadas  
- Java  
- Selenium WebDriver  
- JUnit  
- WebDriverManager  

## Como executar os testes  
1. Clone o repositório  
2. Instale as dependências  
3. Execute os testes com seu framework de testes favorito  

## Exemplo de teste  
```java
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