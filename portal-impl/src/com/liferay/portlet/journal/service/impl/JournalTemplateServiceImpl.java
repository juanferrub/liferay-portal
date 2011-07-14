/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.base.JournalTemplateServiceBaseImpl;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;
import com.liferay.portlet.journal.service.permission.JournalTemplatePermission;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalTemplateServiceImpl extends JournalTemplateServiceBaseImpl {

	public JournalTemplate addTemplate(
			long groupId, String templateId, boolean autoTemplateId,
			String structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsl, boolean formatXsl,
			String langType, boolean cacheable, ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.addTemplate(
			getUserId(), groupId, templateId, autoTemplateId, structureId,
			nameMap, descriptionMap, xsl, formatXsl, langType, cacheable, false,
			null, null, serviceContext);
	}

	public JournalTemplate addTemplate(
			long groupId, String templateId, boolean autoTemplateId,
			String structureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String xsl,
			boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.addTemplate(
			getUserId(), groupId, templateId, autoTemplateId, structureId,
			nameMap, descriptionMap, xsl, formatXsl, langType, cacheable,
			smallImage, smallImageURL, smallFile, serviceContext);
	}

	public JournalTemplate copyTemplate(
			long groupId, String oldTemplateId, String newTemplateId,
			boolean autoTemplateId)
		throws PortalException, SystemException {

		JournalPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_TEMPLATE);

		return journalTemplateLocalService.copyTemplate(
			getUserId(), groupId, oldTemplateId, newTemplateId, autoTemplateId);
	}

	public void deleteTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.DELETE);

		journalTemplateLocalService.deleteTemplate(groupId, templateId);
	}

	public List<JournalTemplate> getStructureTemplates(
			long groupId, String structureId)
		throws PortalException, SystemException {

		if (!JournalStructurePermission.contains(
				getPermissionChecker(), groupId, structureId,
				ActionKeys.VIEW)) {

			return new ArrayList<JournalTemplate>();
		}

		List<JournalTemplate> list =
			journalTemplateLocalService.getStructureTemplates(
				groupId, structureId);

		list = ListUtil.copy(list);

		Iterator<JournalTemplate> itr = list.iterator();

		while (itr.hasNext()) {
			JournalTemplate template = itr.next();

			if (!JournalTemplatePermission.contains(
					getPermissionChecker(), template, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return list;
	}

	public JournalTemplate getTemplate(long groupId, String templateId)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.VIEW);

		return journalTemplateLocalService.getTemplate(groupId, templateId);
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.UPDATE);

		return journalTemplateLocalService.updateTemplate(
			groupId, templateId, structureId, nameMap, descriptionMap, xsl,
			formatXsl, langType, cacheable, false, null, null, serviceContext);
	}

	public JournalTemplate updateTemplate(
			long groupId, String templateId, String structureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String xsl, boolean formatXsl, String langType, boolean cacheable,
			boolean smallImage, String smallImageURL, File smallFile,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalTemplatePermission.check(
			getPermissionChecker(), groupId, templateId, ActionKeys.UPDATE);

		return journalTemplateLocalService.updateTemplate(
			groupId, templateId, structureId, nameMap, descriptionMap, xsl,
			formatXsl, langType, cacheable, smallImage, smallImageURL,
			smallFile, serviceContext);
	}

}