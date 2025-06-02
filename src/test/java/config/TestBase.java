package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestBase {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private static final String PASTA_EVIDENCIAS = "target/evidencias/";
    private static String pastaAtual;

    public static void setupDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            
            // Configurações específicas para ambiente CI
            if (System.getenv("CI") != null) {
                options.addArguments("--headless");  // Modo headless para CI
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
    }

    public static void definirCenario(String nomeCenario) {
        // Define pasta com base no tipo de cenário
        if (nomeCenario.toLowerCase().contains("sucesso")) {
            pastaAtual = PASTA_EVIDENCIAS + "positivo/";
        } else if (nomeCenario.toLowerCase().contains("usuário inválido")) {
            pastaAtual = PASTA_EVIDENCIAS + "negativo/usuario_invalido/";
        } else if (nomeCenario.toLowerCase().contains("senha inválida")) {
            pastaAtual = PASTA_EVIDENCIAS + "negativo/senha_invalida/";
        } else {
            pastaAtual = PASTA_EVIDENCIAS + "negativo/outros/";
        }
        // Garante que a pasta existe
        File pasta = new File(pastaAtual);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    public static void capturarTela(String nomeEvidencia) {
        try {
            if (pastaAtual == null) {
                pastaAtual = PASTA_EVIDENCIAS;
            }
            String caminhoEvidencia = pastaAtual + nomeEvidencia + ".png";
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(caminhoEvidencia));
        } catch (IOException e) {
            System.out.println("Ocorreu erro ao salvar evidência: " + e.getMessage());
        }
    }

    public static void encerrar() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        pastaAtual = null;
    }
} 