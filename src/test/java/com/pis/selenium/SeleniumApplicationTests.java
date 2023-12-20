package com.pis.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest
class SeleniumApplicationTests {

    public static final String URL = "https://pis-selenium-fe.up.railway.app/";
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = WebDriverManager.chromiumdriver().create();
        webDriver.get(URL);
        webDriver.manage().window().maximize();
    }

	@Test
    public void testValidRegistration() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
        WebElement emailField = webDriver.findElement(By.id("email"));
        WebElement passwordField = webDriver.findElement(By.id("password"));
        WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
        WebElement submitButton = webDriver.findElement(By.id("submit-btn"));

		fullNameField.sendKeys("Test Test");
        emailField.sendKeys("test@test.com");
        passwordField.sendKeys("Test@12345");
        confirmPasswordField.sendKeys("Test@12345");
        submitButton.click();

		// Assuming that after successful registration, there is a success message
        WebElement successMessage = webDriver.findElement(By.className("p-toast-message-content"));
		assertEquals("Success\nRegistered successfully", successMessage.getText());
    }

	@Test
	public void testMissingFullName() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
		WebElement emailField = webDriver.findElement(By.id("email"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
		WebElement submitButton = webDriver.findElement(By.id("submit-btn"));
		String classAttributeValue = submitButton.getAttribute("class");
		boolean isPDisabled = classAttributeValue.contains("p-disabled");
		assertEquals(true, isPDisabled);

		fullNameField.sendKeys("");
		emailField.sendKeys("test@test.com");
		passwordField.sendKeys("Test@12345");
		confirmPasswordField.sendKeys("Test@12345");

		WebElement errorMessage = webDriver.findElement(By.cssSelector("#fullName + .p-error"));
		assertEquals("Name is required.", errorMessage.getText());
	}

	@Test
	public void testInvalidFullName() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
		WebElement emailField = webDriver.findElement(By.id("email"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
		WebElement submitButton = webDriver.findElement(By.id("submit-btn"));
		String classAttributeValue = submitButton.getAttribute("class");
		boolean isPDisabled = classAttributeValue.contains("p-disabled");
		assertEquals(true, isPDisabled);

		fullNameField.sendKeys(" ");
		emailField.sendKeys("test@test.com");
		passwordField.sendKeys("Test@12345");
		confirmPasswordField.sendKeys("Test@12345");

		WebElement errorMessage = webDriver.findElement(By.cssSelector("#fullName + .p-error"));
		assertEquals("Enter a valid Name", errorMessage.getText());
	}

	@Test
	public void testInvalidEmail() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
		WebElement emailField = webDriver.findElement(By.id("email"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
		WebElement submitButton = webDriver.findElement(By.id("submit-btn"));
		String classAttributeValue = submitButton.getAttribute("class");
		boolean isPDisabled = classAttributeValue.contains("p-disabled");
		assertEquals(true, isPDisabled);

		fullNameField.sendKeys("Test Test");
		emailField.sendKeys("invalidemail");
		passwordField.sendKeys("Test@12345");
		confirmPasswordField.sendKeys("Test@12345");

		WebElement errorMessage = webDriver.findElement(By.cssSelector("#email + .p-error"));
		assertEquals("Email should be valid", errorMessage.getText());
	}

	@Test
	public void testShortPassword() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
		WebElement emailField = webDriver.findElement(By.id("email"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
		WebElement submitButton = webDriver.findElement(By.id("submit-btn"));
		String classAttributeValue = submitButton.getAttribute("class");
		boolean isPDisabled = classAttributeValue.contains("p-disabled");
		assertEquals(true, isPDisabled);

		fullNameField.sendKeys("Test Test");
		emailField.sendKeys("test@test.com");
		passwordField.sendKeys("123");
		confirmPasswordField.sendKeys("123");

		WebElement errorMessage = webDriver.findElement(By.cssSelector("#password + .p-error"));
		assertEquals("Password must be at least 6 characters long.", errorMessage.getText());
	}

	@Test
	public void testPasswordsDoNotMatch() {
		WebElement fullNameField = webDriver.findElement(By.id("fullName"));
		WebElement emailField = webDriver.findElement(By.id("email"));
		WebElement passwordField = webDriver.findElement(By.id("password"));
		WebElement confirmPasswordField = webDriver.findElement(By.id("confirmPassword"));
		WebElement submitButton = webDriver.findElement(By.id("submit-btn"));
		String classAttributeValue = submitButton.getAttribute("class");
		boolean isPDisabled = classAttributeValue.contains("p-disabled");
		assertEquals(true, isPDisabled);

		fullNameField.sendKeys("Test Test");
		emailField.sendKeys("test@test.com");
		passwordField.sendKeys("Test@12345");
		confirmPasswordField.sendKeys("MismatchedPassword");
		
		WebElement errorMessage = webDriver.findElement(By.cssSelector("#confirmPassword + .p-error"));
		assertEquals("Password should match", errorMessage.getText());
	}
}