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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.base.SocialRequestServiceBaseImpl;
import com.liferay.portlet.social.service.permission.SocialPermission;
import com.liferay.portlet.social.service.permission.SocialRequestEntryPermission;

/**
 * The implementation of the social request remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.social.service.SocialRequestService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.base.SocialRequestServiceBaseImpl
 * @see com.liferay.portlet.social.service.SocialRequestServiceUtil
 */
public class SocialRequestServiceImpl extends SocialRequestServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.social.service.SocialRequestServiceUtil} to access the social request remote service.
	 */

	/**
	 * Adds a social request to the database.
	 *
	 * <p>
	 * In order to add a social request, both the requesting user and the
	 * receiving user must be from the same company and neither of them can be
	 * the default user.
	 * </p>
	 *
	 * @param  userId the primary key of the requesting user
	 * @param  groupId the primary key of the group
	 * @param  className the class name of the asset that is the subject of the
	 *         request
	 * @param  classPK the primary key of the asset that is the subject of the
	 *         request
	 * @param  type the request's type
	 * @param  extraData the extra data regarding the request
	 * @param  receiverUserId the primary key of the user receiving the request
	 * @return the social request
	 * @throws PortalException if the users could not be found, if the users
	 *         were not from the same company, or if either of the users was the
	 *         default user
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest addRequest(
		long userId, long groupId, String className, long classPK, int type,
		String extraData, long receiverUserId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		SocialPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return socialRequestLocalService.addRequest(
			userId, groupId, className, classPK, type, extraData,
			receiverUserId, serviceContext);
	}

	/**
	 * Updates the social request replacing its status.
	 *
	 * <p>
	 * If the status is updated to {@link
	 * com.liferay.portlet.social.model.SocialRequestConstants#STATUS_CONFIRM}
	 * then {@link
	 * com.liferay.portlet.social.service.SocialRequestInterpreterLocalService#processConfirmation(
	 * SocialRequest, ThemeDisplay)} is called. If the status is updated to
	 * {@link
	 * com.liferay.portlet.social.model.SocialRequestConstants#STATUS_IGNORE}
	 * then {@link
	 * com.liferay.portlet.social.service.SocialRequestInterpreterLocalService#processRejection(
	 * SocialRequest, ThemeDisplay)} is called.
	 * </p>
	 *
	 * @param  requestId the primary key of the social request
	 * @param  status the new status
	 * @param  themeDisplay the theme display
	 * @return the updated social request
	 * @throws PortalException if the social request could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRequest updateRequest(
			long requestId, int status, ThemeDisplay themeDisplay,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		SocialRequestEntryPermission.check(
			getPermissionChecker(), requestId, ActionKeys.UPDATE);

		return socialRequestLocalService.updateRequest(
			requestId, status, themeDisplay, serviceContext);
	}

}