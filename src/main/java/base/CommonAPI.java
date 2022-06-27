package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public class CommonAPI {
    public WebDriver driver;
    String currentDir = System.getProperty("user.dir");
    String browserstackUsername= "";
    String browserstackPassword= "";
    String saucelabsUsername="";
    String saucelabsPassword="";

    public void getCloudDriver(String envName, String username, String password, String os, String osVersion, String browser, String browserVersion) throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("os", os);
        cap.setCapability("os_version", osVersion);
        cap.setCapability("browser", browser);
        cap.setCapability("browser_version", browserVersion);
        if (envName.equalsIgnoreCase("browserstack")) {
            cap.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL("http://" + username + ":" + password + "@hub-cloud.browserstack.com:80/wd/hub"), cap);
        } else if (envName.equalsIgnoreCase("saucelabs")){
            driver = new RemoteWebDriver(new URL("http://" + username + ":" + password + "@ondemand.saucelabs.com:80/wd/hub"), cap);
    }


    }
    @Parameters({"useCloudEnv","envName","os","osVersion","browser","browserVersion","url"})
    @BeforeMethod
    public void setUp(boolean useCloudEnv,String envName, String os, String osVersion, String browser, String browserVersion) {
        if(useCloudEnv){
            if (envName.equalsIgnoreCase("browserstack")){
                getCloudDriver(envName, browserstackUsername, browserstackPassword, os, osVersion, browser, browserVersion);
            }else if (envName.equalsIgnoreCase("saucelabs")){
                getCloudDriver(envName, saucelabsUsername, saucelabsPassword, os, osVersion, browser, browserVersion);
            }
        }else {
            getLocalDriver();
        }
        if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", currentDir +"/driver/chromedriver");
            driver = new ChromeDriver();
        }else if (browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver", currentDir +"/driver/geckodriver");
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.get("https://expertautomationteam.com/");
    }
    public void getLocalDriver(String os, String browser){
        if (os.equalsIgnoreCase("OS X")){
            if (browser.equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver", currentDir+"/driver/mac/chromedriver");
                driver = new ChromeDriver();
            }else if (browser.equalsIgnoreCase("firefox")){
                System.setProperty("webdriver.gecko.driver",currentDir+"/driver/mac/geckodriver");
                driver = new FirefoxDriver();
            }
        } else if (os.equalsIgnoreCase("windows")){
            if (browser.equalsIgnoreCase("chrome")){
                System.setProperty("webdriver.chrome.driver",currentDir+"\\driver\\windows\\chromedriver.exe");
                driver = new ChromeDriver();
            }else if (browser.equalsIgnoreCase("firefox")){
                System.setProperty("webdriver.gecko.driver",currentDir+"\\driver\\windows\\geckodriver.exe");
                driver = new FirefoxDriver();
            }
        }
    }
    @AfterMethod
    public void tearDown() {
        driver.close();
    }
    public String getPagetitle(){
      return driver.getTitle();
    }

    public String getElementText(String locator) {
        try {
            return driver.findElement(By.cssSelector(locator)).getText();
        } catch (Exception e) {
            return driver.findElement(By.xpath(locator)).getText();
        }
    }

    public void click(String locator) {
        try {
            driver.findElement(By.cssSelector(locator)).click();
        } catch (Exception e) {
            driver.findElement(By.xpath(locator)).click();
        }
    }

    public void type(String locator, String text) {
        try {
            driver.findElement(By.cssSelector(locator)).sendKeys(text);
        } catch (Exception e) {
            driver.findElement(By.xpath(locator)).sendKeys(text);
        }
    }
    public boolean isPresent(String locator){
        try{
            return driver.findElement(By.cssSelector(locator)).isDisplayed();
        }catch (Exception e){
            return driver.findElement(By.xpath(locator)).isDisplayed();
        }
    }
}
