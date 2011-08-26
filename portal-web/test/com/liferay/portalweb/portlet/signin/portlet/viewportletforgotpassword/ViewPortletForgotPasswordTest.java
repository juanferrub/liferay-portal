/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.signin.portlet.viewportletforgotpassword;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletForgotPasswordTest extends BaseTestCase {
	public void testViewPortletForgotPassword() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Sign In Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Sign In Test Page",
			RuntimeVariables.replace("Sign In Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Forgot Password"),
			selenium.getText("//li[3]/a/span"));
		selenium.clickAt("//li[3]/a/span",
			RuntimeVariables.replace("Forgot Password"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("//input[@id='_58_emailAddress']"));
		selenium.type("//input[@id='_58_emailAddress']",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_58_captchaText']",
			RuntimeVariables.replace("1111"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace("Text verification failed."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
		assertTrue(selenium.isVisible("//input[@id='_58_emailAddress']"));
		selenium.type("//input[@id='_58_emailAddress']",
			RuntimeVariables.replace("testfake@liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_58_captchaText']",
			RuntimeVariables.replace("1111"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace("Text verification failed."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}