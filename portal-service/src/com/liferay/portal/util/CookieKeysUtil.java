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

import com.liferay.portal.CookieNotSupportedException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Minhchau Dang
 * @author Juan Fernández
 */
public class CookieKeysUtil {

	public static final String COMPANY_ID = "COMPANY_ID";

	public static final String COOKIE_SUPPORT = "COOKIE_SUPPORT";

	public static final String GUEST_LANGUAGE_ID = "GUEST_LANGUAGE_ID";

	public static final String ID = "ID";

	public static final String JSESSIONID = "JSESSIONID";

	public static final String LOGIN = "LOGIN";

	public static final int MAX_AGE = 31536000;

	public static final String PASSWORD = "PASSWORD";

	public static final String REMEMBER_ME = "REMEMBER_ME";

	public static final String SCREEN_NAME = "SCREEN_NAME";

	public static final String USER_UUID = "USER_UUID";

	public static final int VERSION = 0;

	public static void addCookie(
		HttpServletRequest request, HttpServletResponse response,
		Cookie cookie) {

		getCookieKeys().addCookie(request, response, cookie);
	}

	public static void addCookie(
		HttpServletRequest request, HttpServletResponse response, Cookie cookie,
		boolean secure) {

		getCookieKeys().addCookie(request, response, cookie, secure);
	}

	public static void addSupportCookie(
		HttpServletRequest request, HttpServletResponse response) {

		getCookieKeys().addSupportCookie(request, response);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		return getCookieKeys().getCookie(request, name);
	}

	public static String getCookie(
		HttpServletRequest request, String name, boolean toUpperCase) {

		return getCookieKeys().getCookie(request, name, toUpperCase);
	}

	public static CookieKeys getCookieKeys() {
		PortalRuntimePermission.checkGetBeanProperty(CookieKeysUtil.class);

		return _cookieKeys;
	}

	public static String getDomain(HttpServletRequest request) {
		return getCookieKeys().getDomain(request);
	}

	public static String getDomain(String host) {
		return getCookieKeys().getDomain(host);
	}

	public static boolean hasSessionId(HttpServletRequest request) {
		return getCookieKeys().hasSessionId(request);
	}

	public static boolean isEncodedCookie(String name) {
		return getCookieKeys().isEncodedCookie(name);
	}

	public static void validateSupportCookie(HttpServletRequest request)
		throws CookieNotSupportedException {

		getCookieKeys().validateSupportCookie(request);
	}

	public void setCookieKeys(CookieKeys cookieKeys) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_cookieKeys = cookieKeys;
	}

	private static CookieKeys _cookieKeys;

}