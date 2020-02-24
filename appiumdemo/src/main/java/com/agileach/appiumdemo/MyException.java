package com.agileach.appiumdemo;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyException extends Exception{		
	private static final long serialVersionUID = 1L;	
	String dateTime = Util.getFormatedTime("yyyy-MM-dd HH_mm_ss_SSS");
	StringBuffer sb = new StringBuffer();
	String captureName = sb.append(dateTime).append(".png").toString();			
	Logger log = LoggerFactory.getLogger(this.getClass());	
	
	public MyException(Exception e, WebDriver driver) {		
		log.error(e.getLocalizedMessage());					
		Util.takeScreenshot(driver, captureName);
	}	
}
