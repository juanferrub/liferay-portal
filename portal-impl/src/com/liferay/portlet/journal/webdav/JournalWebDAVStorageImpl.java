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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateServiceUtil;
import com.liferay.portlet.dynamicdatamapping.webdav.DDMWebDavUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import jodd.util.LocaleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.deleteResource(
			webDavRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		return DDMWebDavUtil.getResource(
			webDavRequest, getRootPath(), getToken()
			, PortalUtil.getClassNameId(JournalArticle.class));
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			if (pathArray.length == 2) {
				return getFolders(webDavRequest);
			}
			else if (pathArray.length == 3) {
				String type = pathArray[2];

				if (type.equals(DDMWebDavUtil.TYPE_STRUCTURES)) {
					return getStructures(webDavRequest);
				}
				else if (type.equals(DDMWebDavUtil.TYPE_TEMPLATES)) {
					return getTemplates(webDavRequest);
				}
			}

			return new ArrayList<Resource>();
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		return DDMWebDavUtil.putResource(
			webDavRequest, getRootPath(), getToken(),
			PortalUtil.getClassNameId(JournalArticle.class));
	}

	protected List<Resource> getFolders(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> folders = new ArrayList<Resource>();

		folders.add(
			DDMWebDavUtil.toResource(
				webDavRequest, DDMWebDavUtil.TYPE_STRUCTURES, getRootPath(),
				true));
		folders.add(
			DDMWebDavUtil.toResource(
				webDavRequest, DDMWebDavUtil.TYPE_TEMPLATES, getRootPath(),
				true));

		return folders;
	}

	protected List<Resource> getStructures(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures(
				webDavRequest.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class));

		for (DDMStructure ddmStructure : ddmStructures) {
			Resource resource = DDMWebDavUtil.toResource(
				webDavRequest, ddmStructure, getRootPath(), true);

			resources.add(resource);
		}

		return resources;
	}

	protected List<Resource> getTemplates(WebDAVRequest webDavRequest)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures(
				webDavRequest.getGroupId(),
				PortalUtil.getClassNameId(JournalArticle.class));

		for (DDMStructure ddmStructure : ddmStructures) {
			List<DDMTemplate> ddmTemplates =
				DDMTemplateLocalServiceUtil.getTemplates(
					webDavRequest.getGroupId(),
					PortalUtil.getClassNameId(DDMStructure.class),
					ddmStructure.getStructureId());

			for (DDMTemplate ddmTemplate : ddmTemplates) {
				Resource resource = DDMWebDavUtil.toResource(
					webDavRequest, ddmTemplate, getRootPath(), true);

				resources.add(resource);
			}
		}

		return resources;
	}

}