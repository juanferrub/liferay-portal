/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@RunWith(PowerMockRunner.class)
public class HtmlImplTest extends PowerMockito {

	@Before
	public void setUp() {
		_htmlImpl = new HtmlImpl();
	}

	@Test
	public void testContainsScriptTag() throws Exception {
		Assert.assertTrue(_htmlImpl.containsScriptTag(HTML_WITH_SCRIPT));
	}

	@Test
	public void testContainsScriptTagEmpty() throws Exception {
		Assert.assertTrue(_htmlImpl.containsScriptTag(HTML_WITH_SCRIPT_EMPTY));
	}

	@Test
	public void testContainsScriptTagInside() throws Exception {
		Assert.assertTrue(_htmlImpl.containsScriptTag(HTML_WITH_SCRIPT_INSIDE));
	}

	@Test
	public void testContainsScriptTagNoScript() throws Exception {
		Assert.assertFalse(_htmlImpl.containsScriptTag(HTML_WITHOUT_SCRIPT));
	}

	private static final String HTML_WITH_SCRIPT =
		"<script>alert('XSS');</script>";

	private static final String HTML_WITH_SCRIPT_EMPTY = "<script/>";

	private static final String HTML_WITH_SCRIPT_INSIDE =
		"<div><p><script>alert('XSS');</script></p></div>";

	private static final String HTML_WITHOUT_SCRIPT = "<b>Hello world!</b>";

	private HtmlImpl _htmlImpl;

}