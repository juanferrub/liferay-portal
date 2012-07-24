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

package com.liferay.portlet.social.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialRequestService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestService
 * @generated
 */
public class SocialRequestServiceWrapper implements SocialRequestService,
	ServiceWrapper<SocialRequestService> {
	public SocialRequestServiceWrapper(
		SocialRequestService socialRequestService) {
		_socialRequestService = socialRequestService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialRequestService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRequestService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a social request to the database.
	*
	* <p>
	* In order to add a social request, both the requesting user and the
	* receiving user must be from the same company and neither of them can be
	* the default user.
	* </p>
	*
	* @param userId the primary key of the requesting user
	* @param groupId the primary key of the group
	* @param className the class name of the asset that is the subject of the
	request
	* @param classPK the primary key of the asset that is the subject of the
	request
	* @param type the request's type
	* @param extraData the extra data regarding the request
	* @param receiverUserId the primary key of the user receiving the request
	* @return the social request
	* @throws PortalException if the users could not be found, if the users
	were not from the same company, or if either of the users was the
	default user
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestService.addRequest(userId, groupId, className,
			classPK, type, extraData, receiverUserId, serviceContext);
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
	* @param requestId the primary key of the social request
	* @param status the new status
	* @param themeDisplay the theme display
	* @return the updated social request
	* @throws PortalException if the social request could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestService.updateRequest(requestId, status,
			themeDisplay, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialRequestService getWrappedSocialRequestService() {
		return _socialRequestService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialRequestService(
		SocialRequestService socialRequestService) {
		_socialRequestService = socialRequestService;
	}

	public SocialRequestService getWrappedService() {
		return _socialRequestService;
	}

	public void setWrappedService(SocialRequestService socialRequestService) {
		_socialRequestService = socialRequestService;
	}

	private SocialRequestService _socialRequestService;
}