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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleException extends PortalException {

	public LocaleException() {
		super();
	}

	public LocaleException(String msg) {
		super(msg);
	}

	public LocaleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LocaleException(Throwable cause) {
		super(cause);
	}

	public Locale[] getSourceAvailableLocales() {
		return _sourceAvailableLocales;
	}

	public String getSourceAvailableLocalesString() {
		return StringUtil.merge(
			_sourceAvailableLocales, StringPool.COMMA_AND_SPACE);
	}

	public Locale[] getTargetAvailableLocales() {
		return _targetAvailableLocales;
	}

	public String getTargetAvailableLocalesString() {
		return StringUtil.merge(
			_targetAvailableLocales, StringPool.COMMA_AND_SPACE);
	}

	public void setSourceAvailableLocales(Locale[] sourceAvailableLocales) {
		_sourceAvailableLocales = sourceAvailableLocales;
	}

	public void setTargetAvailableLocales(Locale[] targetAvailableLocales) {
		_targetAvailableLocales = targetAvailableLocales;
	}

	private Locale[] _sourceAvailableLocales;
	private Locale[] _targetAvailableLocales;

}