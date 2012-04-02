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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.BaseDLAppTestCase;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryTrashHandlerTest extends BaseDLAppTestCase {

	@Test
	public void testDeleteTrashEntries() {

		AssetEntry assetEntry = null;
		FileEntry dlFileEntry = null;
		TrashEntry trashEntry = null;

		try {
			dlFileEntry = addFileEntry(true, "Test.pdf");
			FileVersion dlFileVersion = dlFileEntry.getFileVersion();

			User user = UserLocalServiceUtil.getUserById(
				dlFileEntry.getUserId());

			assetEntry = AssetEntryLocalServiceUtil.createAssetEntry(
				dlFileEntry.getFileEntryId());

			assetEntry.setGroupId(dlFileEntry.getGroupId());
			assetEntry.setCompanyId(dlFileEntry.getCompanyId());
			assetEntry.setUserId(dlFileEntry.getUserId());
			assetEntry.setUserName(user.getFullName());
			assetEntry.setCreateDate(dlFileEntry.getCreateDate());
			assetEntry.setClassNameId(
				PortalUtil.getClassNameId(FileEntry.class.getName()));
			assetEntry.setClassPK(dlFileEntry.getFileEntryId());

			AssetEntryLocalServiceUtil.addAssetEntry(assetEntry);

			trashEntry = TrashEntryLocalServiceUtil.addTrashEntry(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
				PortalUtil.getClassNameId(FileEntry.class),
				dlFileEntry.getPrimaryKey(), dlFileVersion.getStatus(), null);
		}
		catch (SystemException e) {
			Assert.fail("Unexpected error moving the file to trash");
		}
		catch (Exception e) {
			Assert.fail("Unexpected error adding the file");
		}
		finally {
			try {
				_cleanUpTempFiles(assetEntry, dlFileEntry, trashEntry);
			}
			catch (Exception e) {
				Assert.fail("Unexpected error deleting the file");
			}
		}
	}

	/**
	 * Cleans the temporary files created in the test
	 *
	 * @param assetEntry The AssetEntry to be deleted
	 * @param dlFileEntry The DLFileEntry to be deleted
	 * @param trashEntry The TrashEntry to be deleted
	 * @throws Exception if an exception is ocurred
	 */
	private void _cleanUpTempFiles(
			AssetEntry assetEntry, FileEntry dlFileEntry, TrashEntry trashEntry)
		throws Exception {

		if (dlFileEntry != null) {
			DLFileEntryLocalServiceUtil.deleteDLFileEntry(
				dlFileEntry.getFileEntryId());

			if (assetEntry != null) {
				AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);
			}

			if (trashEntry != null) {
				TrashEntryLocalServiceUtil.deleteTrashEntry(trashEntry);
			}
		}
	}

}