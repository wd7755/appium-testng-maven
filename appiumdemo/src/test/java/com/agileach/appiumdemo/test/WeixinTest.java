package com.agileach.appiumdemo.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class WeixinTest {
	private AppiumDriver<WebElement> driver;

	@BeforeClass
	public void setUp() throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();		
//		File classpathRoot = new File(System.getProperty("user.dir"));       
//        File appDir = new File(classpathRoot, "app");
//        File app = new File(appDir, "weixin7010android1580.apk");
//     	cap.setCapability("app", app.getAbsolutePath());			
		cap.setCapability("platformName", "Android"); // 指定测试平台		
		cap.setCapability("platformVersion", "5.1.1");
		cap.setCapability("androidInstallTimeout", "600000");
		cap.setCapability("uiautomator2ServerInstallTimeout", "180000");
		cap.setCapability("adbExecTimeout", "180000");
		cap.setCapability("udid", "c167c65a");		
		// 将上面获取到的包名和Activity名设置为值
		cap.setCapability("appPackage", "com.tencent.mm");
		cap.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");			
		// A new session could not be created的解决方法		
		cap.setCapability("noReset", true);
		cap.setCapability("unicodeKeyboard", true);// 使用Unicode输入法，默认 false
		cap.setCapability("resetKeyboard", true);// 在使用了unicode输入法测试结束后，重置输入法到原有状态。如果单独使用，将会被忽略。默认值 false
		// 每次启动时覆盖session，否则第二次后运行会报错不能新建session
		cap.setCapability("sessionOverride", true);
		driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@AfterClass
	public void tearDown() throws Exception {
		Thread.sleep(10000);
		driver.quit();
	}
	@Test
	public void sendText() throws InterruptedException, IOException {
		Thread.sleep(5000);
		List<WebElement> list = driver.findElementsById("com.tencent.mm:id/bah");// 这就是通过驱动找元素（定位到微信页面上的每一个聊天框，因为每个框的id是一样的）
		//int width = driver.manage().window().getSize().width;
		//int height = driver.manage().window().getSize().height;
		// 找到第三个对话框
		WebElement target = list.get(0);
		if (target != null) {
			target.click();// 点击进入某某某的对话框		
			// 点击打开对话框的输入框
			WebElement input = driver.findElementById("com.tencent.mm:id/aqe");
			input.click();
			input.sendKeys("这是我的测试脚本发送的信息");// 这个是你要发送的消息		
			driver.findElementById("com.tencent.mm:id/aql").click();// 这个是点击发送按钮
			driver.findElementById("com.tencent.mm:id/ls").click();// 这个是点击返回<按钮			
		} else {
			//driver.(width / 2, height * 3 / 4, width / 2, height / 4, 800);
			//Thread.sleep(100);
			//list = driver.findElementsById("com.tencent.mm:id/apt");
		}
	}
	@Test
	public void sendToMoments() throws InterruptedException {
		try {			
			List<WebElement> list = driver.findElementsById("com.tencent.mm:id/dkb");// 通过驱动找元素
			if (CollectionUtils.isNotEmpty(list)) {
				// 找到第三个图标：发现
				//WebElement target = list.get(2);				
				WebElement target = getTarget(list);
				System.out.println(target.getText());
				if (target != null) {
					target.click();// 点击发现					
				}
				//点击朋友圈
				List<WebElement> list1 = driver.findElementsById("android:id/title");
				if (CollectionUtils.isNotEmpty(list1)) {
					// 找到第1个框：朋友圈
					WebElement target1 = list1.get(0);// 朋友圈
					System.out.println(target1.getText());
					if (target1 != null) {
						target1.click();// 点击进入朋友圈						
					}
				}
				// 找到发朋友圈按钮
				WebElement webElement3 = driver.findElementById("com.tencent.mm:id/lo");
				if (webElement3 != null) {
					System.out.println(webElement3.getText());
					webElement3.click();// 点击相机的icon					
				}
				// 点击从相册选取
				List<WebElement> list3 = driver.findElementsById("com.tencent.mm:id/gbm");
				if (CollectionUtils.isNotEmpty(list3)) {
					if (CollectionUtils.isNotEmpty(list3)) {
						WebElement element = list3.get(1);
						System.out.println(element.getText());
						element.click();
					}
				}				
				// 选择第一张照片
				List<WebElement> list4 = driver.findElementsById("com.tencent.mm:id/bws");
				if (CollectionUtils.isNotEmpty(list4)) {				
					//选择九张照片
					for (int i = 0; i < 9; i++) {
						WebElement target4 = list4.get(i);
						if (target4 != null) {						
							target4.click();// 点击选择照片
						}
					}
				}
				// 点击完成
				WebElement webElement5 = driver.findElementById("com.tencent.mm:id/ln");
				if (webElement5 != null) {
					System.out.println(webElement5.getText());
					webElement5.click();// 点击完成					
				}
				//这是朋友圈文字
				driver.findElementById("com.tencent.mm:id/d41").sendKeys("这是我的自动化脚本发送的第一条朋友圈，还是很开心");
				//Thread.sleep(500);
				driver.findElementById("com.tencent.mm:id/ln").click();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getStackTrace());
		}
	}		
	
	private WebElement getTarget(List<WebElement> list) throws InterruptedException {
		for (WebElement w : list) {
			System.out.println(w.getText());
			if (w.getText().contains("发现")) {
				return w;
			}
		}
		return null;
	}
}