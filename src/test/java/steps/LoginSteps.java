package steps;

import config.TestBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.E;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;


public class LoginSteps extends TestBase {
    private Scenario cenarioAtual;
    private static final String URL_LOGIN = "https://practicetestautomation.com/practice-test-login/";

    @Before
    public void inicializar(Scenario cenario) {
        this.cenarioAtual = cenario;
        setupDriver();
        definirCenario(cenario.getName());
    }

    @After
    public void finalizar() {
        encerrar();
    }

    @Dado("que estou na página de login")
    public void acessarPaginaLogin() {
        driver.get(URL_LOGIN);
        capturarTela("01_pagina_login");
    }

    @Quando("preencho o usuário {string}")
    public void preencherUsuario(String usuario) {
        // Localiza e preenche o campo de usuário
        WebElement campoUsuario = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        campoUsuario.clear();
        campoUsuario.sendKeys(usuario);
    }

    @E("preencho a senha {string}")
    public void preencherSenha(String senha) {
        // Localiza e preenche o campo de senha
        WebElement campoSenha = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        campoSenha.clear();
        campoSenha.sendKeys(senha);
        capturarTela("02_campos_preenchidos");
    }

    @E("clico no botão submit")
    public void clicarSubmit() {
        // Localiza e clica no botão de login
        WebElement botaoSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        botaoSubmit.click();
    }

    @Então("devo ser redirecionado para a página de sucesso")
    public void verificarRedirecionamento() {
        // Verifica se foi redirecionado para a página correta
        wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
        String urlAtual = driver.getCurrentUrl();
        assertTrue("Não foi redirecionado para a página de sucesso. URL atual: " + urlAtual, 
                  urlAtual.contains("logged-in-successfully"));
    }

    @E("devo ver a mensagem de login realizado com sucesso")
    public void verificarMensagemSucesso() {
        WebElement textoSucesso = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        String mensagem = textoSucesso.getText().toLowerCase();
        assertTrue("A mensagem de sucesso não foi encontrada na página", 
                  mensagem.contains("logged in successfully") || 
                  mensagem.contains("congratulations") ||
                  mensagem.contains("successfully logged in"));
        capturarTela("03_login_sucesso");
    }

    @Então("devo ver a mensagem de erro {string}")
    public void verificarMensagemErro(String mensagemEsperada) {
        // Aguarda e verifica a mensagem de erro
        WebElement mensagemErro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        String mensagemAtual = mensagemErro.getText();
        assertTrue("Mensagem de erro incorreta. Esperado: '" + mensagemEsperada + "' Atual: '" + mensagemAtual + "'",
                  mensagemAtual.equals(mensagemEsperada));
        capturarTela("04_erro_login");
    }

    @E("devo ver o botão de logout")
    public void verificarBotaoLogout() {
        // Verifica se o botão de logout está visível após o login
        WebElement botaoLogout = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Log out")));
        assertTrue("O botão de logout não está visível na página após o login", 
                  botaoLogout.isDisplayed());
        capturarTela("05_logout_visivel");
    }
} 