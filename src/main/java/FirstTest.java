import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FirstTest {
    String P2P_URL = "https://next.privat24.ua/mobile";

    @Test
    void test() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
        By phoneNumber = By.xpath("//input[@data-qa-node='phone-number']");
        By amount50 = By.xpath("//button[@type='button' and contains(., '50')]");
        By amount100 = By.xpath("//button[@type='button' and contains(., '100')]");
        By amount150 = By.xpath("//button[@type='button' and contains(., '150')]");
        By amount200 = By.xpath("//button[@type='button' and contains(., '200')]");
        By amount = By.xpath("//input[@data-qa-node='amount']");
        By cardNumber = By.xpath("//input[@data-qa-node='numberdebitSource']");
        By expireDate = By.xpath("//input[@data-qa-node='expiredebitSource']");
        By cvvNumber = By.xpath("//input[@data-qa-node='cvvdebitSource']");
        By termAndCondition = By.xpath("//a[@href='https://privatbank.ua/terms']");
        By submitButton = By.xpath("//button[@data-qa-node='submit']");
        By name = By.xpath("//input[@data-qa-node='firstNamedebitSource']");
        By surname = By.xpath("//input[@data-qa-node='lastNamedebitSource']");
        By numberCheck = By.xpath("//div[@data-qa-node='details']");
        By amountCheck = By.xpath("//div[@data-qa-node='amount']");

        // действия над элементами

        driver.get(P2P_URL);
        driver.findElement(phoneNumber).sendKeys("996010099");
        Thread.sleep(2000);
        driver.findElement(amount50).click();
        Thread.sleep(2000);
        driver.findElement(amount100).click();
        Thread.sleep(2000);
        driver.findElement(amount150).click();
        Thread.sleep(2000);
        driver.findElement(amount200).click();
        Thread.sleep(2000);
        String selectAll;
        String os = System.getProperty("os.name");

        // Выбор операционой системы
        if (os.equals("WINDOWS")){
            selectAll=Keys.chord(Keys.CONTROL, "A");
        }else{
            selectAll=Keys.chord(Keys.COMMAND, "A");
        }

        driver.findElement(amount).sendKeys(selectAll); // срабатывает не всегда но изменив заглавную на прописную работает снова и обратно
        driver.findElement(amount).sendKeys(Keys.DELETE);
        Thread.sleep(2000);
        driver.findElement(amount).sendKeys("777");
        Thread.sleep(2000);
        driver.findElement(cardNumber).sendKeys("4004159115449003");
        driver.findElement(expireDate).sendKeys("1223");
        driver.findElement(cvvNumber).sendKeys("111");
    //--------------------------------------------------------------------------------------------
        driver.findElement(termAndCondition).click();
        driver.switchTo().window((String)driver.getWindowHandles().toArray()[1]);
        //ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        //driver.switchTo().window(tabs2.get(1));
        Thread.sleep(10000);
        System.out.println("Титул Страница 2: " + driver.getTitle());
        Assertions.assertEquals("Умови та правила", driver.getTitle());
        Thread.sleep(4000);
        driver.close();
        //driver.switchTo().window(tabs2.get(0));
    // --------------------------------------------------------------------------------------------


        driver.switchTo().window((String)driver.getWindowHandles().toArray()[0]);
        driver.findElement(name).sendKeys("Oleh");
        driver.findElement(surname).sendKeys("Pysarenko");

        driver.findElement(submitButton).click();
//      Извлекаем строку c номером мобильного
        String resultNumberText = driver.findElement(numberCheck).getText();
//      Извлекаем номер из строки для сравнения
        String resultPhone = resultNumberText.substring(resultNumberText.indexOf('+'), resultNumberText.length());
        System.out.println("Извлеченный номер из строки: " + resultPhone);
//      Извлекаем строку c суммой пополнения для сравнения
        String resultAmount = driver.findElement(amountCheck).getText();

        System.out.println("Фактическая сумма пополнения мобильного: " +resultAmount);

//      Проверока соответсвия номера для пополнения
        Assertions.assertEquals("+380996010099", resultPhone);
//      Сравнение суммы пополнения мобильного
        Assertions.assertEquals("777 UAH", resultAmount);
//      driver.close();
    }
}
