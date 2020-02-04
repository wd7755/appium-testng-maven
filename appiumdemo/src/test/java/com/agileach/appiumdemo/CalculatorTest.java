package com.agileach.appiumdemo;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import org.testng.Assert;
import org.testng.annotations.*;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CalculatorTest {
	private static RemoteWebDriver CalculatorSession = null;
	private static WebElement CalculatorResult = null;

	@BeforeClass
	public static void setup() {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 30);
			capabilities.setCapability("appWaitDuration", 30000);
			capabilities.setCapability("deviceReadyTimeout", 5);
			//capabilities.setCapability("udid", "H9407AN002114");
			capabilities.setCapability("appPackage", "com.android.calculator2");
			capabilities.setCapability("appActivity", ".Calculator");
			// 每次启动时覆盖session，否则第二次后运行会报错不能新建session
			capabilities.setCapability("sessionOverride", true);
			// 支持中文
			capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
			capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
			capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
			CalculatorSession = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			System.out.println("APP starting...");
			Thread.sleep(5000);// 有的app打开慢，加大等待时间
			// 如果是安卓虚拟机，4723不好使的话要改成虚拟机进程的PID，可以打开任务管理器查看进程详细信息来获得		
			CalculatorSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@AfterClass
	public static void TearDown() {
		CalculatorResult = null;
		if (CalculatorSession != null) {
			CalculatorSession.quit();
		}
		CalculatorSession = null;
	}

	@Test
	public void Addition() {
		WebElement one = CalculatorSession.findElementById("com.android.calculator2:id/digit_1");
		one.click();
		CalculatorSession.findElementById("com.android.calculator2:id/op_add").click();
		CalculatorSession.findElementById("com.android.calculator2:id/digit_7").click();
		CalculatorSession.findElementById("com.android.calculator2:id/eq").click();
		CalculatorResult = CalculatorSession.findElementById("com.android.calculator2:id/result");
		Assert.assertEquals("8", CalculatorResult.getAttribute("text"));
	}

	@DataProvider(name = "testdata")
	public Object[][] getData() {
		return new Object[][] { { "20", "80", "100", "add" }, { "90", "3", "270", "mul" }, { "6", "2", "3", "div" },
				{ "9", "2", "7", "sub" } };
	}

	@Test(dataProvider = "testdata")
	public void calcTestcase(String num1, String num2, String result, String calcType) {
		for (char num : num1.toCharArray()) {
			CalculatorSession.findElement(By.id("com.android.calculator2:id/digit_" + String.valueOf(num))).click();
		}
		CalculatorSession.findElement(By.id("com.android.calculator2:id/op_" + calcType)).click();
		for (char num : num2.toCharArray()) {
			CalculatorSession.findElement(By.id("com.android.calculator2:id/digit_" + String.valueOf(num))).click();
		}
		CalculatorSession.findElement(By.id("com.android.calculator2:id/eq")).click();		
		CalculatorResult = CalculatorSession.findElementById("com.android.calculator2:id/result");
		String value = CalculatorResult.getAttribute("text");
		Assert.assertEquals(value, result);
	}
}
