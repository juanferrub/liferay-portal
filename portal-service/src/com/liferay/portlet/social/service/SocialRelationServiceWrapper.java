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
 * This class is a wrapper for {@link SocialRelationService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationService
 * @generated
 */
public class SocialRelationServiceWrapper implements SocialRelationService,
	ServiceWrapper<SocialRelationService> {
	public SocialRelationServiceWrapper(
		SocialRelationService socialRelationService) {
		_socialRelationService = socialRelationService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialRelationService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRelationService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialRelationService getWrappedSocialRelationService() {
		return _socialRelationService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialRelationService(
		SocialRelationService socialRelationService) {
		_socialRelationService = socialRelationService;
	}

	public SocialRelationService getWrappedService() {
		return _socialRelationService;
	}

	public void setWrappedService(SocialRelationService socialRelationService) {
		_socialRelationService = socialRelationService;
	}

	private SocialRelationService _socialRelationService;
}