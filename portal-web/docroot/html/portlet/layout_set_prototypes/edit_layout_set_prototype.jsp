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

<%@ include file="/html/portlet/layout_set_prototypes/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)request.getAttribute(WebKeys.LAYOUT_PROTOTYPE);

if (layoutSetPrototype == null) {
	layoutSetPrototype = new LayoutSetPrototypeImpl();

	layoutSetPrototype.setNew(true);
	layoutSetPrototype.setActive(true);
}

long layoutSetPrototypeId = BeanParamUtil.getLong(layoutSetPrototype, request, "layoutSetPrototypeId");

boolean layoutsUpdateable = GetterUtil.getBoolean(layoutSetPrototype.getSettingsProperty("layoutsUpdateable"), true);

Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();
%>

<liferay-util:include page="/html/portlet/layout_set_prototypes/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= layoutSetPrototype.isNew() ? "add" : "view-all" %>' />
</liferay-util:include>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= layoutSetPrototype.isNew() %>"
	title='<%= layoutSetPrototype.isNew() ? "new-site-template" : layoutSetPrototype.getName(locale) %>'
/>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveLayoutSetPrototype();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototypeId %>" />

	<aui:model-context bean="<%= layoutSetPrototype %>" model="<%= LayoutSetPrototype.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<aui:input name="active" />

		<aui:input helpMessage="allow-site-administrators-to-modify-pages-associated-with-this-site-template-help" label="allow-site-administrators-to-modify-pages-associated-with-this-site-template" name="layoutsUpdateable" type="checkbox" value="<%= layoutsUpdateable %>" />

		<c:if test="<%= !layoutSetPrototype.isNew() %>">
			<aui:field-wrapper label="configuration">
				<liferay-portlet:actionURL portletName="<%= PortletKeys.SITE_REDIRECTOR %>" var="viewURL">
					<portlet:param name="struts_action" value="/my_sites/view" />
					<portlet:param name="groupId" value="<%= String.valueOf(layoutSetPrototype.getGroup().getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:actionURL>

				<liferay-ui:icon
					image="view"
					label="<%= true %>"
					message="open-site-template"
					method="get"
					target="_blank"
					url="<%= viewURL %>"
				/>
			</aui:field-wrapper>

			<aui:field-wrapper label="merge-fail-count[site-template]">

				<%
					LayoutSet layoutSetPrototypeLayoutSet = layoutSetPrototype.getLayoutSet();

					UnicodeProperties layoutSetPrototypeLayoutSetSettingsProperties =
							layoutSetPrototypeLayoutSet.getSettingsProperties();

					int mergeFailCount = GetterUtil.getInteger(
							layoutSetPrototypeLayoutSetSettingsProperties.getProperty("merge-fail-count"), 0);
				%>

				<%= mergeFailCount %>

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">

					<div class="portlet-msg-alert">
						<liferay-ui:message key="merge-fail-count-exceeds-threshold-x-no-further-merges-will-be-done"
											arguments="<%= new Object[]{PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD} %>" />

						<aui:button onClick='<%= renderResponse.getNamespace() + "resetMergeFailCount()" %>'
									value="reset-merge-fail-count" />

					</div>

				</c:if>
			</aui:field-wrapper>
		</c:if>

		<%
		Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();

		String customJspServletContextName = StringPool.BLANK;

		if (layoutSetPrototype != null) {
			UnicodeProperties settingsProperties = layoutSetPrototype.getSettingsProperties();

			customJspServletContextName = GetterUtil.getString(settingsProperties.get("customJspServletContextName"));
		}
		%>

		<c:if test="<%= !servletContextNames.isEmpty() %>">
			<aui:select label="application-adapter" name="customJspServletContextName">
				<aui:option label="none" />

				<%
				for (String servletContextName : servletContextNames) {
				%>

					<aui:option selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>"><%= CustomJspRegistryUtil.getDisplayName(servletContextName) %></aui:option>

				<%
				}
				%>

			</aui:select>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayoutSetPrototype() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (layoutSetPrototype == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" /></portlet:actionURL>");
	}

	function <portlet:namespace />resetMergeFailCount() {

		<portlet:renderURL var="currentEditURL">
			<portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="layoutSetPrototypeId" value="<%= String.valueOf(layoutSetPrototypeId) %>" />
		</portlet:renderURL>

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.RESET_MERGE_FAIL_COUNT %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= currentEditURL %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/layout_set_prototypes/edit_layout_set_prototype" /></portlet:actionURL>");
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (!layoutSetPrototype.isNew()) {
	PortalUtil.addPortletBreadcrumbEntry(request, layoutSetPrototype.getName(locale), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-page"), currentURL);
}
%>