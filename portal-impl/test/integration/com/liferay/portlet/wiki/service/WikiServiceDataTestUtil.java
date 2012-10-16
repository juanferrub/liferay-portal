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

package com.liferay.portlet.wiki.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WikiServiceDataTestUtil {

	public static WikiNode addNode()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), "Test Name", "Test Description",
			serviceContext);
	}

	public static WikiPage addPage(long nodeId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		return WikiPageLocalServiceUtil.addPage(
			TestPropsValues.getUserId(), nodeId, "Test title", "Test Content",
			"Test Summary", false, serviceContext);
	}

}