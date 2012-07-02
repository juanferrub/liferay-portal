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
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
public abstract class BaseAssetRenderer implements AssetRenderer {

	public AssetRendererFactory getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getAssetRendererFactoryClassName());

		return _assetRendererFactory;
	}

	public String[] getAvailableLocales() {
		return _AVAILABLE_LOCALES;
	}

	public String getDiscussionPath() {
		return null;
	}

	public String getIconPath() {
		return getIconPath(_portletRequest);
	}

	public String getIconPath(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	public PortletRequest getPortletRequest() {
		return _portletRequest;
	}

	public PortletResponse getPortletResponse() {
		return _portletResponse;
	}

	public String getSummary() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay == null) {
			return null;
		}

		return getSummary(themeDisplay.getLocale());
	}

	public ThemeDisplay getThemeDisplay() {
		if ((_themeDisplay == null) && (_portletRequest != null)) {
			_themeDisplay = (ThemeDisplay)_portletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		}

		return _themeDisplay;
	}

	public String getTitle() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay == null) {
			return null;
		}

		return getTitle(themeDisplay.getLocale());
	}

	public String getURLDownload() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay == null) {
			return null;
		}

		return getURLDownload(_themeDisplay);
	}

	public String getURLDownload(ThemeDisplay themeDisplay) {
		return null;
	}

	public PortletURL getURLEdit() throws Exception {
		if ((_portletRequest == null) || (_portletResponse == null)) {
			return null;
		}

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_portletRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		return getURLEdit(liferayPortletRequest, liferayPortletResponse);
	}

	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	public PortletURL getURLExport() throws Exception {
		if ((_portletRequest == null) || (_portletResponse == null)) {
			return null;
		}

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_portletRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		return getURLExport(liferayPortletRequest, liferayPortletResponse);
	}

	public PortletURL getURLExport(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return null;
	}

	public String getUrlTitle() {
		return null;
	}

	public PortletURL getURLView(WindowState windowState) throws Exception {
		if ((_portletRequest == null) || (_portletResponse == null)) {
			return null;
		}

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		return getURLView(liferayPortletResponse, windowState);
	}

	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		return null;
	}

	public String getURLViewInContext(String noSuchEntryRedirect)
		throws Exception {

		if ((_portletRequest == null) || (_portletResponse == null)) {
			return null;
		}

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(_portletRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		return getURLViewInContext(
			liferayPortletRequest, liferayPortletResponse, noSuchEntryRedirect);
	}

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		return null;
	}

	public String getViewInContextMessage() {
		return "view-in-context";
	}

	@SuppressWarnings("unused")
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return false;
	}

	@SuppressWarnings("unused")
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return true;
	}

	public void initForRender(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
	}

	public boolean isConvertible() {
		return false;
	}

	public boolean isDisplayable() {
		return true;
	}

	public boolean isLocalizable() {
		return false;
	}

	public boolean isPreviewInContext() {
		return false;
	}

	public boolean isPrintable() {
		return false;
	}

	public String render(String template) throws Exception {
		if ((_portletRequest == null) || (_portletResponse == null) ||
			(!(_portletRequest instanceof RenderRequest) &&
			 !(_portletResponse instanceof RenderResponse))) {

			return null;
		}

		return render(
			(RenderRequest)_portletRequest, (RenderResponse)_portletResponse,
			template);
	}

	protected long getControlPanelPlid(
			LiferayPortletRequest liferayPortletRequest)
		throws PortalException, SystemException {

		HttpServletRequest request =
			liferayPortletRequest.getHttpServletRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group controlPanelGroup = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.CONTROL_PANEL);

		return LayoutLocalServiceUtil.getDefaultPlid(
			controlPanelGroup.getGroupId(), true);
	}

	protected long getControlPanelPlid(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		Group controlPanelGroup = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.CONTROL_PANEL);

		return LayoutLocalServiceUtil.getDefaultPlid(
			controlPanelGroup.getGroupId(), true);
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	protected String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest, String noSuchEntryRedirect,
		String path, String primaryKeyParameterName,
		long primaryKeyParameterValue) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(11);

		sb.append(themeDisplay.getPortalURL());
		sb.append(themeDisplay.getPathMain());
		sb.append(path);
		sb.append("?p_l_id=");
		sb.append(themeDisplay.getPlid());
		sb.append("&noSuchEntryRedirect=");
		sb.append(HttpUtil.encodeURL(noSuchEntryRedirect));
		sb.append(StringPool.AMPERSAND);
		sb.append(primaryKeyParameterName);
		sb.append(StringPool.EQUAL);
		sb.append(primaryKeyParameterValue);

		return sb.toString();
	}

	private static final String[] _AVAILABLE_LOCALES = new String[0];

	private AssetRendererFactory _assetRendererFactory;
	private PortletRequest _portletRequest;
	private PortletResponse _portletResponse;
	private ThemeDisplay _themeDisplay;

}