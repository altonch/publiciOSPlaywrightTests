This is a public repository for Playwright tests that work with the iOS Playwright Beta. <br>

This works with the Perfecto brand, a product of Perforce.<br>

For details of this product integration, please see the official help documentation from Perfecto.<br>

https://help.perfecto.io/perfecto-help/content/perfecto/automation-testing/playwright-integrate-w-perfecto-ios.htm<br>

How This Works:<br>
This sample uses a security token to access the cloud.<br>

Under the myUtilities folder is a logins.java file.<br>

Update your cloud short name and the security token in the logins.java file for this parameter.

			Map.entry("testing", "abcd1234")

This test itself uses two static variables.<br>

	private static String host = "testing";
	private static String myDUT = "abcd1234";

Set the host value to the cloud name.<br>

Set the myDUT value to the device ID of the iOS device that will be used for testing.<br>

Note: These tests are provided AS IS. Support is not included with these test samples. <br>
