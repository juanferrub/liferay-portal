<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String displayStyle = PrefsParamUtil.getString(preferences, renderRequest, "displayStyle", "horizontal");

Boolean showCurrentGroup = PrefsParamUtil.getBoolean(preferences, renderRequest, "showCurrentGroup", true);
Boolean showCurrentPortlet = PrefsParamUtil.getBoolean(preferences, renderRequest, "showCurrentPortlet", true);
Boolean showGuestGroup = PrefsParamUtil.getBoolean(preferences, renderRequest, "showGuestGroup", true);
Boolean showLayout = PrefsParamUtil.getBoolean(preferences, renderRequest, "showLayout", true);
Boolean showParentGroups = PrefsParamUtil.getBoolean(preferences, renderRequest, "showParentGroups", true);
Boolean showPortletBreadcrumb = PrefsParamUtil.getBoolean(preferences, renderRequest, "showPortletBreadcrumb", true);
%>

<%@ include file="/html/portlet/breadcrumb/init-ext.jsp" %>