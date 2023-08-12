package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import manage.DatabaseManager;

public class MyTestClass {

	@Test
	public void testValidation() {
		String testName = "Kajetan";
		String testAddress = "Politechnika 2";
		String testPhone = "192837465";
		String testWeb = "http://kajetan.ugu.pl";
		String testEmail = "kajetan@student.pwr.pl";
		
		assertTrue(DatabaseManager.ValidateData(testName, testAddress, testPhone, testWeb, testEmail) == null);
	}

}
