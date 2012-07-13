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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * The interface for the social relation remote service.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialRelationServiceUtil
 * @see com.liferay.portlet.social.service.base.SocialRelationServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialRelationServiceImpl
 * @generated
 */
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SocialRelationService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialRelationServiceUtil} to access the social relation remote service. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialRelationServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Adds a social relation between the two users to the database.
	*
	* @param userId1 the user that is the subject of the relation
	* @param userId2 the user at the other end of the relation
	* @param type the type of the relation
	* @return the social relation
	* @throws PortalException if the users could not be found, if the users
	were not from the same company, or if either of the users was the
	default user
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the matching relation (and its inverse in case of a bidirectional
	* relation) from the database.
	*
	* @param userId1 the user that is the subject of the relation
	* @param userId2 the user at the other end of the relation
	* @param type the relation's type
	* @throws PortalException if the relation or its inverse relation (if
	applicable) could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the relation (and its inverse in case of a bidirectional
	* relation) from the database.
	*
	* @param relation the relation to be removed
	* @throws PortalException if the relation is bidirectional and its inverse
	relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all relations between User1 and User2.
	*
	* @param userId1 the user that is the subject of the relation
	* @param userId2 the user at the other end of the relation
	* @throws PortalException if the inverse relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRelations(long userId1, long userId2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}