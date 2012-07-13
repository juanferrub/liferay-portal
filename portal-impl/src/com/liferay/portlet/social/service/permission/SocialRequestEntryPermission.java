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

package com.liferay.portlet.social.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

/**
 * @author Ivica Cardic
 */
public class SocialRequestEntryPermission {
	public static void check(
		PermissionChecker permissionChecker, long requestId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, requestId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, SocialRequest socialRelation,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, socialRelation, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long requestId, String actionId)
		throws PortalException, SystemException {

		SocialRequest socialRelation =
			SocialRequestLocalServiceUtil.getSocialRequest(requestId);

		return contains(permissionChecker, socialRelation, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SocialRequest socialRequest,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
			socialRequest.getCompanyId(), SocialRequest.class.getName(),
			socialRequest.getRequestId(), socialRequest.getUserId(),
			actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			socialRequest.getGroupId(), SocialRequest.class.getName(),
			socialRequest.getRequestId(), actionId);
	}

}