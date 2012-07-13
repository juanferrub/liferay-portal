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

package com.liferay.portlet.social.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.social.service.SocialRelationServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.social.service.SocialRelationServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.social.model.SocialRelationSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.social.model.SocialRelation}, that is translated to a
 * {@link com.liferay.portlet.social.model.SocialRelationSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/api/secure/axis. Set the property
 * <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationServiceHttp
 * @see       com.liferay.portlet.social.model.SocialRelationSoap
 * @see       com.liferay.portlet.social.service.SocialRelationServiceUtil
 * @generated
 */
public class SocialRelationServiceSoap {
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
	public static com.liferay.portlet.social.model.SocialRelationSoap addRelation(
		long userId1, long userId2, int type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.social.model.SocialRelation returnValue = SocialRelationServiceUtil.addRelation(userId1,
					userId2, type, serviceContext);

			return com.liferay.portlet.social.model.SocialRelationSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

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
	public static void deleteRelation(long userId1, long userId2, int type)
		throws RemoteException {
		try {
			SocialRelationServiceUtil.deleteRelation(userId1, userId2, type);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes the relation (and its inverse in case of a bidirectional
	* relation) from the database.
	*
	* @param relation the relation to be removed
	* @throws PortalException if the relation is bidirectional and its inverse
	relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRelation(
		com.liferay.portlet.social.model.SocialRelationSoap relation)
		throws RemoteException {
		try {
			SocialRelationServiceUtil.deleteRelation(com.liferay.portlet.social.model.impl.SocialRelationModelImpl.toModel(
					relation));
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Removes all relations between User1 and User2.
	*
	* @param userId1 the user that is the subject of the relation
	* @param userId2 the user at the other end of the relation
	* @throws PortalException if the inverse relation could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRelations(long userId1, long userId2)
		throws RemoteException {
		try {
			SocialRelationServiceUtil.deleteRelations(userId1, userId2);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SocialRelationServiceSoap.class);
}