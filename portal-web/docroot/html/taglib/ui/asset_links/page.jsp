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

<%@ include file="/html/taglib/ui/asset_links/init.jsp" %>

<%
long assetEntryId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:asset-links:assetEntryId"));

List<AssetLink> assetLinks = null;

if (assetEntryId > 0) {
	assetLinks = AssetLinkLocalServiceUtil.getDirectLinks(assetEntryId);
}
%>

<c:if test="<%= (assetLinks != null) && !assetLinks.isEmpty() %>">
	<div class="taglib-asset-links">
		<h2 class="asset-links-title"><liferay-ui:message key="related-assets" />:</h2>

		<ul class="asset-links-list">

			<%
			for (AssetLink assetLink : assetLinks) {
				AssetEntry assetLinkEntry = null;

				if (assetLink.getEntryId1() == assetEntryId) {
					assetLinkEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId2());
				}
				else {
					assetLinkEntry = AssetEntryServiceUtil.getEntry(assetLink.getEntryId1());
				}

				if (!assetLinkEntry.isVisible()) {
					continue;
				}

				assetLinkEntry = assetLinkEntry.toEscapedModel();

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(assetLinkEntry.getClassNameId()));

				AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(assetLinkEntry.getClassPK());

				if (assetRenderer.hasViewPermission(permissionChecker)) {
					assetRenderer.initForRender(renderRequest, renderResponse);

					String asseLinktEntryTitle = assetLinkEntry.getTitle();

					LiferayPortletURL assetPublisherURL = new PortletURLImpl(request, PortletKeys.ASSET_PUBLISHER, plid, PortletRequest.RENDER_PHASE);

					assetPublisherURL.setWindowState(WindowState.MAXIMIZED);

					assetPublisherURL.setParameter("struts_action", "/asset_publisher/view_content");
					assetPublisherURL.setParameter("assetEntryId", String.valueOf(assetLinkEntry.getEntryId()));
					assetPublisherURL.setParameter("type", assetRendererFactory.getType());

					if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
						if (assetRenderer.getGroupId() != scopeGroupId) {
							assetPublisherURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
						}

						assetPublisherURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
					}

					String viewFullContentURLString = assetPublisherURL.toString();

					viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

					String urlViewInContext = assetRenderer.getURLViewInContext(viewFullContentURLString);
			%>

					<li class="asset-links-list-item">
						<liferay-ui:icon
							label="<%= true %>"
							message="<%= asseLinktEntryTitle %>"
							src="<%= assetRenderer.getIconPath() %>"
							url="<%= urlViewInContext %>"
						/>
					</li>

			<%
				}
			}
			%>

		</ul>
	</div>
</c:if>