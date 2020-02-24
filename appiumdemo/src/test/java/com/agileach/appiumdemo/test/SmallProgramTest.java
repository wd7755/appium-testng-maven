package com.agileach.appiumdemo.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.agileach.appiumdemo.MyException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class SmallProgramTest {
	private AndroidDriver<WebElement> appiumDriver;
	// private final static Logger log =
	// LoggerFactory.getLogger(SmallProgramTest.class.getClass());
	int MAX_TIMEOUT_SECONDS = 30;

	@BeforeClass(alwaysRun = true)
	public void setcapability() throws MyException {
		// com.tencent.mm:appbrand0
		DesiredCapabilities cap = new DesiredCapabilities();
//		File classpathRoot = new File(System.getProperty("user.dir"));       
//        File appDir = new File(classpathRoot, "app");
//        File app = new File(appDir, "weixin7010android1580.apk");
		// cap.setCapability("app", app.getAbsolutePath());
		// 将上面获取到的包名和Activity名设置为值
		cap.setCapability("appPackage", "com.tencent.mm");
		cap.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		// cap.setCapability("appWaitActivity",
		// "com.tencent.mm.app.WeChatSplashActivity");
		cap.setCapability("platformName", "Android"); // 指定测试平台
		// cap.setCapability("platformName", "iOS"); // 指定测试平台
		// cap.setCapability("deviceName", "emulator-5556"); // 指定测试机的ID,通过adb命令`adb
		// devices`获取 .测试设备类型（测试Android时被忽略）
		cap.setCapability("platformVersion", "5.1.1");// 指定手机的安卓版本
		cap.setCapability("udid", "c167c65a");
		// cap.setCapability("udid", "c318e7b4b5ee287684642d91edf1c673761463a4");
		// cap.setCapability("automationName", "UiAutomator2");
		cap.setCapability("newCommandTimeout", "5000");
		cap.setCapability("adbExecTimeout", "180000");
		cap.setCapability("appWaitDuration", 30000);
		cap.setCapability("deviceReadyTimeout", 5);
		cap.setCapability("androidInstallTimeout", "600000");
		cap.setCapability("uiautomator2ServerInstallTimeout", "180000");
		cap.setCapability("recreateChromeDriverSessions", true);
		cap.setCapability("unicodeKeyboard", true);// 使用Unicode输入法，默认 false
		cap.setCapability("resetKeyboard", true);// 在使用了unicode输入法测试结束后，重置输入法到原有状态。如果单独使用，将会被忽略。默认值 false
		cap.setCapability("noReset", true);// 每次启动时不清楚应用数据启动（不需要每次微信登录、授权等）
		cap.setCapability("noSign", true); // 不重新签名apk
		// 每次启动时覆盖session，否则第二次后运行会报错不能新建session
		cap.setCapability("sessionOverride", true);
		// ChromeOptions使用来定制启动选项，因为在appium中切换context识别webview的时候,把com.tencent.mm:toolsmp的webview识别成com.tencent.mm的webview.
		// 所以为了避免这个问题，加上androidProcess名
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("androidProcess", "com.tencent.mm:appbrand0");
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		// 初始化会默认将chrome浏览器打开，需要将Browser置为空
		cap.setBrowserName("");
		try {
			appiumDriver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException(e, appiumDriver);
		}
		appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void plus() throws MyException {
		// 休眠一下
		// Thread.sleep(5000);
		// 找到微信的发现并点击
		try {
			List<WebElement> list = appiumDriver.findElementsById("com.tencent.mm:id/dkb");// 通过驱动找元素
			if (CollectionUtils.isNotEmpty(list)) {
				// 找到第三个图标：发现
				// WebElement target = list.get(2);
				WebElement target = getTarget(list, "发现");
				if (target != null) {
					target.click();// 点击发现
				}
			}
			// Thread.sleep(5000);
			// 点击搜一搜
			List<WebElement> list1 = appiumDriver.findElementsById("android:id/title");
			if (CollectionUtils.isNotEmpty(list1)) {
				// 找到第5个框：搜一搜
				WebElement target1 = getTarget(list1, "搜一搜");
				if (target1 != null) {
					target1.click();// 点击进入朋友圈
				}
			}
			// Thread.sleep(2000);
			// 点击搜索框
			appiumDriver.findElement(By.id("com.tencent.mm:id/m7")).click();
			appiumDriver.findElement(By.id("com.tencent.mm:id/m7")).sendKeys("猜歌王");
			// Thread.sleep(3000);
			// 点击猜歌王小程序
			appiumDriver.findElementByXPath("//android.view.View[@text='猜歌王小程序']").click();
			System.out.println("1");
			Thread.sleep(4000);
			// 点击搜索结果中的猜歌王（采用adb命令坐标点击的方式）
			Runtime.getRuntime().exec("adb shell input tap 400 400");
			System.out.println("2");
			// 等待小程序加载完成
			// Thread.sleep(10000);
			// 获取到所有的contexts
			System.out.println("所有的contexts:" + appiumDriver.getContextHandles());
			// 切换到最新的小程序webview对应的context中
			switchToLastWebView();
			System.out.println("3");
			Thread.sleep(2000);
			// 获取到所有的handles
			Set<String> windowHandles = appiumDriver.getWindowHandles();
			System.out.println(windowHandles.size());
			System.out.println("所有的windowsHandles" + windowHandles);
			// 遍历所有的handles，找到当前页面所在的handle：如果pageSource有包含你想要的元素，就是所要找的handle
			// 小程序的页面来回切换也需要：遍历所有的handles，切换到元素所在的handle
			for (String windowHandle : windowHandles) {
				System.out.println("切换到对应的windowHandle：" + windowHandle);
				appiumDriver.switchTo().window(windowHandle);
				// Thread.sleep(2000);
				if (appiumDriver.getPageSource().contains("排位赛")) {
					System.out.println("4");
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MyException(e, appiumDriver);
		} // 需要算出屏幕的宽和高
		System.out.println("5");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws MyException {
		// Thread.sleep(5000);
		appiumDriver.quit();
	}

	/**
	 * Switch to NATIVE_APP or WEBVIEW
	 * 
	 * @param sWindow window name
	 * @throws MyException
	 */
	private void switchToLastWebView() throws MyException {
		Set<String> contextNames = appiumDriver.getContextHandles();
		List<String> webViewContextNames = contextNames.stream().filter(contextName -> contextName.contains("WEBVIEW_"))
				.collect(Collectors.toList());
		String currentContextView = "";
		if (webViewContextNames.size() > 0) {
			currentContextView = (String) webViewContextNames.toArray()[webViewContextNames.size() - 1];
			try {
				appiumDriver.context(currentContextView);
			} catch (Exception e) {
				e.printStackTrace();
				throw new MyException(e, appiumDriver);
			}
		}
	}

	public void switchToSpecificWebView(By selector) throws MyException {
		switchToLastWebView();
		long begin = System.currentTimeMillis();
		do {
			try {
				List<WebElement> elements = appiumDriver.findElements(selector);
				if (null != elements && elements.size() > 0) {
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			switchToLastWebView();
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while ((System.currentTimeMillis() - begin) < MAX_TIMEOUT_SECONDS * 1000);
	}

	private WebElement getTarget(List<WebElement> list, String text) throws MyException {
		for (WebElement w : list) {
			if (w.getText().contains(text)) {
				return w;
			}
		}
		return null;
	}
}
