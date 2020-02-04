package com.agileach.appiumdemo.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class Calculator {
	
	@FindBy(id = "com.android.calculator2:id/op_add")
	@CacheLookup
	private WebElement add;
	
	@FindBy(id = "com.android.calculator2:id/eq")
	@CacheLookup
	private WebElement equal;
	
	@FindBy(id = "com.android.calculator2:id/result")
	@CacheLookup
	private WebElement result;
}
