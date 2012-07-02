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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

/**
 * @author Jorge Ferrer
 * @author Juan Fernández
 */
public interface AssetRenderer {

	public static final String TEMPLATE_ABSTRACT = "abstract";

	public static final String TEMPLATE_FULL_CONTENT = "full_content";

	public String getAssetRendererFactoryClassName();

	public String[] getAvailableLocales() throws Exception;

	public long getClassPK();

	public String getDiscussionPath();

	public long getGroupId();

	public String getIconPath();

	/**
	 * @deprecated see {@link #getIconPath()}
	 */
	public String getIconPath(PortletRequest portletRequest);

	public String getSummary();

	public String getSummary(Locale locale);

	public String getTitle();

	public String getTitle(Locale locale);

	public String getURLDownload();

	/**
	 * @deprecated see {@link #getURLDownload()}
	 */
	public String getURLDownload(ThemeDisplay themeDisplay);

	public PortletURL getURLEdit() throws Exception;

	/**
	 * @deprecated see {@link #getURLEdit()}
	 */
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public PortletURL getURLExport() throws Exception;

	/**
	 * @deprecated see {@link #getURLExport()}
	 */
	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public String getUrlTitle();

	public PortletURL getURLView(WindowState windowState) throws Exception;

	/**
	 * @deprecated see {@link #getURLView()}
	 */
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception;

	public String getURLViewInContext(String noSuchEntryRedirect)
		throws Exception;

	/**
	 * @deprecated see {@link #getURLViewInContext(String)}
	 */
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception;

	public long getUserId();

	public String getUserName();

	public String getUuid();

	public String getViewInContextMessage();

	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException;

	public void initForRender(
		PortletRequest portletRequest, PortletResponse portletResponse);

	public boolean isConvertible();

	public boolean isDisplayable();

	public boolean isLocalizable();

	public boolean isPreviewInContext();

	public boolean isPrintable();

	public String render(String template) throws Exception;

	/**
	 * @deprecated see {@link #render(String)}
	 */
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception;

}