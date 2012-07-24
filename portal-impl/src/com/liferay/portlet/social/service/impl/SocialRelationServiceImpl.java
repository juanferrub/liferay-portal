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
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.service.base.SocialRelationServiceBaseImpl;
import com.liferay.portlet.social.service.permission.SocialPermission;
import com.liferay.portlet.social.service.permission.SocialRelationEntryPermission;

import java.util.List;

/**
 * The implementation of the social relation remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.social.service.SocialRelationService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.social.service.base.SocialRelationServiceBaseImpl
 * @see com.liferay.portlet.social.service.SocialRelationServiceUtil
 */
public class SocialRelationServiceImpl extends SocialRelationServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.social.service.SocialRelationServiceUtil} to access the social relation remote service.
	 */

	/**
	 * Adds a social relation between the two users to the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the type of the relation
	 * @return the social relation
	 * @throws PortalException if the users could not be found, if the users
	 *         were not from the same company, or if either of the users was the
	 *         default user
	 * @throws SystemException if a system exception occurred
	 */
	public SocialRelation addRelation(
			long userId1, long userId2, int type, ServiceContext serviceContext)
		throws PortalException, SystemException {

		SocialPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_ENTRY);

		return socialRelationLocalService.addRelation(
			userId1, userId2, type, serviceContext);
	}

	/**
	 * Removes the matching relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @param  type the relation's type
	 * @throws PortalException if the relation or its inverse relation (if
	 *         applicable) could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelation(long userId1, long userId2, int type)
		throws PortalException, SystemException {

		SocialRelation relation = socialRelationPersistence.findByU1_U2_T(
			userId1, userId2, type);

		deleteRelation(relation);
	}

	/**
	 * Removes the relation (and its inverse in case of a bidirectional
	 * relation) from the database.
	 *
	 * @param  relation the relation to be removed
	 * @throws PortalException if the relation is bidirectional and its inverse
	 *         relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelation(SocialRelation relation)
		throws PortalException, SystemException {

		SocialRelationEntryPermission.check(
			getPermissionChecker(), relation, ActionKeys.DELETE);

		socialRelationLocalService.deleteRelation(relation);
	}

	/**
	 * Removes all relations between User1 and User2.
	 *
	 * @param  userId1 the user that is the subject of the relation
	 * @param  userId2 the user at the other end of the relation
	 * @throws PortalException if the inverse relation could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteRelations(long userId1, long userId2)
		throws PortalException, SystemException {

		List<SocialRelation> relations = socialRelationPersistence.findByU1_U2(
			userId1, userId2);

		for (SocialRelation relation : relations) {
			deleteRelation(relation);
		}

	}

}