package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.Reporter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class AbstractTest {
    private WebDriver driver;
    Cipher cipher;
    KeyPair pair;
    byte[] cipherText;
    String rootFolder = System.getProperty("user.dir");

    protected final Log log;

    protected AbstractTest() {
        log = LogFactory.getLog(getClass());
    }

    public WebDriver getBrowserDriver(String browserName) {
        switch (browserName) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, rootFolder + "\\FirefoxLogs.txt");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-notifications");
                driver = new FirefoxDriver();
                break;
            case "firefox_headless":
//			System.setProperty("webdriver.gecko.driver", rootFolder + "\\resources\\geckodriver.exe");
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.setHeadless(true);
                driver = new FirefoxDriver(options);
                break;
            case "chrome":
//			System.setProperty("webdriver.chrome.driver", rootFolder + "\\resources\\chromedriver.exe");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
//				chromeOptions.addArguments("--remote-debugging-port=9222");
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//				chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                driver = new ChromeDriver(chromeOptions);
                break;
            case "chrome_headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options2 = new ChromeOptions();
                options2.setHeadless(true);
                driver = new ChromeDriver(options2);
                break;
            default:
                System.out.println("Please input your browser name!");
                break;
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public void closeBrowserAndDriver(WebDriver driver) {
        try {
            // get OS name and convert to lower case
            String osName = System.getProperty("os.name").toLowerCase();
            log.info("OS Name = " + osName);

            // Declare command line to execute
            String cmd = "";
            if (driver != null) {
                driver.quit();
            }

            if (driver.toString().toLowerCase().contains("chrome")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("internetexplorer")) {
                if (osName.toLowerCase().contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driver.toString().toLowerCase().contains("firefox")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            }

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            log.info("----------------QUIT BROWSER SUCCESS-----------------");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            if (condition == true) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;

            // Add lỗi vào ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        System.out.println("Status = " + pass);
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            if (condition == false) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        boolean status;
        try {
            if (actual instanceof String && expected instanceof String) {
                actual = actual.toString().trim();
                log.info("Actual = " + actual);
                expected = expected.toString().trim();
                log.info("Expected = " + expected);
                status = (actual.equals(expected));
            } else {
                status = (actual == expected);
            }

            if (status) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertEquals(actual, expected, "Value is not matching!");
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    public String encryptData(String pass) {
        //Creating a Signature object
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Create Keypair generator object
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Initializing the KeyPairGenerator
        keyPairGen.initialize(2048);
        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();
        //Get the public key from the key pair
        PublicKey publicKey = pair.getPublic();
        //Create a Cipher object
        cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //Init a Cipher object
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        //Add data to the cipher
        byte[] input = pass.getBytes();
        cipher.update(input);
        //Encrypting data
        cipherText = new byte[0];
        try {
            cipherText = cipher.doFinal();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        String cipheredText = null;
        try {
            cipheredText = new String(cipherText, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Initializing the same cipher for decryption
        try {
            cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipheredText;
    }

    public String getDecryptedText(String encryptedText) {
        //Decrypting the text
        byte[] decipheredText = new byte[0];
        try {
            decipheredText = cipher.doFinal(cipherText);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        String decryptedText = null;
        try {
            decryptedText = new String(decipheredText, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decryptedText;
    }


}
