/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.sync.model.SyncDLFileVersionDiff;
import com.liferay.sync.service.SyncDLFileVersionDiffLocalService;
import com.liferay.sync.service.persistence.SyncDLFileVersionDiffPersistence;
import com.liferay.sync.service.persistence.SyncDLObjectFinder;
import com.liferay.sync.service.persistence.SyncDLObjectPersistence;
import com.liferay.sync.service.persistence.SyncDevicePersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the sync d l file version diff local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.sync.service.impl.SyncDLFileVersionDiffLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.sync.service.impl.SyncDLFileVersionDiffLocalServiceImpl
 * @see com.liferay.sync.service.SyncDLFileVersionDiffLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class SyncDLFileVersionDiffLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements SyncDLFileVersionDiffLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.sync.service.SyncDLFileVersionDiffLocalServiceUtil} to access the sync d l file version diff local service.
	 */

	/**
	 * Adds the sync d l file version diff to the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync d l file version diff
	 * @return the sync d l file version diff that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SyncDLFileVersionDiff addSyncDLFileVersionDiff(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {
		syncDLFileVersionDiff.setNew(true);

		return syncDLFileVersionDiffPersistence.update(syncDLFileVersionDiff);
	}

	/**
	 * Creates a new sync d l file version diff with the primary key. Does not add the sync d l file version diff to the database.
	 *
	 * @param syncDLFileVersionDiffId the primary key for the new sync d l file version diff
	 * @return the new sync d l file version diff
	 */
	@Override
	public SyncDLFileVersionDiff createSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) {
		return syncDLFileVersionDiffPersistence.create(syncDLFileVersionDiffId);
	}

	/**
	 * Deletes the sync d l file version diff with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync d l file version diff
	 * @return the sync d l file version diff that was removed
	 * @throws PortalException if a sync d l file version diff with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SyncDLFileVersionDiff deleteSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) throws PortalException {
		return syncDLFileVersionDiffPersistence.remove(syncDLFileVersionDiffId);
	}

	/**
	 * Deletes the sync d l file version diff from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync d l file version diff
	 * @return the sync d l file version diff that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SyncDLFileVersionDiff deleteSyncDLFileVersionDiff(
		SyncDLFileVersionDiff syncDLFileVersionDiff) throws PortalException {
		return syncDLFileVersionDiffPersistence.remove(syncDLFileVersionDiff);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SyncDLFileVersionDiff.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return syncDLFileVersionDiffPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return syncDLFileVersionDiffPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return syncDLFileVersionDiffPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return syncDLFileVersionDiffPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return syncDLFileVersionDiffPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public SyncDLFileVersionDiff fetchSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) {
		return syncDLFileVersionDiffPersistence.fetchByPrimaryKey(syncDLFileVersionDiffId);
	}

	/**
	 * Returns the sync d l file version diff with the primary key.
	 *
	 * @param syncDLFileVersionDiffId the primary key of the sync d l file version diff
	 * @return the sync d l file version diff
	 * @throws PortalException if a sync d l file version diff with the primary key could not be found
	 */
	@Override
	public SyncDLFileVersionDiff getSyncDLFileVersionDiff(
		long syncDLFileVersionDiffId) throws PortalException {
		return syncDLFileVersionDiffPersistence.findByPrimaryKey(syncDLFileVersionDiffId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(syncDLFileVersionDiffLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SyncDLFileVersionDiff.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"syncDLFileVersionDiffId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(syncDLFileVersionDiffLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(SyncDLFileVersionDiff.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"syncDLFileVersionDiffId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(syncDLFileVersionDiffLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SyncDLFileVersionDiff.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"syncDLFileVersionDiffId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return syncDLFileVersionDiffLocalService.deleteSyncDLFileVersionDiff((SyncDLFileVersionDiff)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return syncDLFileVersionDiffPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the sync d l file version diffs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sync.model.impl.SyncDLFileVersionDiffModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sync d l file version diffs
	 * @param end the upper bound of the range of sync d l file version diffs (not inclusive)
	 * @return the range of sync d l file version diffs
	 */
	@Override
	public List<SyncDLFileVersionDiff> getSyncDLFileVersionDiffs(int start,
		int end) {
		return syncDLFileVersionDiffPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of sync d l file version diffs.
	 *
	 * @return the number of sync d l file version diffs
	 */
	@Override
	public int getSyncDLFileVersionDiffsCount() {
		return syncDLFileVersionDiffPersistence.countAll();
	}

	/**
	 * Updates the sync d l file version diff in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param syncDLFileVersionDiff the sync d l file version diff
	 * @return the sync d l file version diff that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SyncDLFileVersionDiff updateSyncDLFileVersionDiff(
		SyncDLFileVersionDiff syncDLFileVersionDiff) {
		return syncDLFileVersionDiffPersistence.update(syncDLFileVersionDiff);
	}

	/**
	 * Returns the sync device local service.
	 *
	 * @return the sync device local service
	 */
	public com.liferay.sync.service.SyncDeviceLocalService getSyncDeviceLocalService() {
		return syncDeviceLocalService;
	}

	/**
	 * Sets the sync device local service.
	 *
	 * @param syncDeviceLocalService the sync device local service
	 */
	public void setSyncDeviceLocalService(
		com.liferay.sync.service.SyncDeviceLocalService syncDeviceLocalService) {
		this.syncDeviceLocalService = syncDeviceLocalService;
	}

	/**
	 * Returns the sync device persistence.
	 *
	 * @return the sync device persistence
	 */
	public SyncDevicePersistence getSyncDevicePersistence() {
		return syncDevicePersistence;
	}

	/**
	 * Sets the sync device persistence.
	 *
	 * @param syncDevicePersistence the sync device persistence
	 */
	public void setSyncDevicePersistence(
		SyncDevicePersistence syncDevicePersistence) {
		this.syncDevicePersistence = syncDevicePersistence;
	}

	/**
	 * Returns the sync d l file version diff local service.
	 *
	 * @return the sync d l file version diff local service
	 */
	public SyncDLFileVersionDiffLocalService getSyncDLFileVersionDiffLocalService() {
		return syncDLFileVersionDiffLocalService;
	}

	/**
	 * Sets the sync d l file version diff local service.
	 *
	 * @param syncDLFileVersionDiffLocalService the sync d l file version diff local service
	 */
	public void setSyncDLFileVersionDiffLocalService(
		SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService) {
		this.syncDLFileVersionDiffLocalService = syncDLFileVersionDiffLocalService;
	}

	/**
	 * Returns the sync d l file version diff persistence.
	 *
	 * @return the sync d l file version diff persistence
	 */
	public SyncDLFileVersionDiffPersistence getSyncDLFileVersionDiffPersistence() {
		return syncDLFileVersionDiffPersistence;
	}

	/**
	 * Sets the sync d l file version diff persistence.
	 *
	 * @param syncDLFileVersionDiffPersistence the sync d l file version diff persistence
	 */
	public void setSyncDLFileVersionDiffPersistence(
		SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence) {
		this.syncDLFileVersionDiffPersistence = syncDLFileVersionDiffPersistence;
	}

	/**
	 * Returns the sync d l object local service.
	 *
	 * @return the sync d l object local service
	 */
	public com.liferay.sync.service.SyncDLObjectLocalService getSyncDLObjectLocalService() {
		return syncDLObjectLocalService;
	}

	/**
	 * Sets the sync d l object local service.
	 *
	 * @param syncDLObjectLocalService the sync d l object local service
	 */
	public void setSyncDLObjectLocalService(
		com.liferay.sync.service.SyncDLObjectLocalService syncDLObjectLocalService) {
		this.syncDLObjectLocalService = syncDLObjectLocalService;
	}

	/**
	 * Returns the sync d l object persistence.
	 *
	 * @return the sync d l object persistence
	 */
	public SyncDLObjectPersistence getSyncDLObjectPersistence() {
		return syncDLObjectPersistence;
	}

	/**
	 * Sets the sync d l object persistence.
	 *
	 * @param syncDLObjectPersistence the sync d l object persistence
	 */
	public void setSyncDLObjectPersistence(
		SyncDLObjectPersistence syncDLObjectPersistence) {
		this.syncDLObjectPersistence = syncDLObjectPersistence;
	}

	/**
	 * Returns the sync d l object finder.
	 *
	 * @return the sync d l object finder
	 */
	public SyncDLObjectFinder getSyncDLObjectFinder() {
		return syncDLObjectFinder;
	}

	/**
	 * Sets the sync d l object finder.
	 *
	 * @param syncDLObjectFinder the sync d l object finder
	 */
	public void setSyncDLObjectFinder(SyncDLObjectFinder syncDLObjectFinder) {
		this.syncDLObjectFinder = syncDLObjectFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.kernel.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.kernel.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the d l app local service.
	 *
	 * @return the d l app local service
	 */
	public com.liferay.document.library.kernel.service.DLAppLocalService getDLAppLocalService() {
		return dlAppLocalService;
	}

	/**
	 * Sets the d l app local service.
	 *
	 * @param dlAppLocalService the d l app local service
	 */
	public void setDLAppLocalService(
		com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.sync.model.SyncDLFileVersionDiff",
			syncDLFileVersionDiffLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.sync.model.SyncDLFileVersionDiff");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SyncDLFileVersionDiffLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SyncDLFileVersionDiff.class;
	}

	protected String getModelClassName() {
		return SyncDLFileVersionDiff.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = syncDLFileVersionDiffPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.sync.service.SyncDeviceLocalService.class)
	protected com.liferay.sync.service.SyncDeviceLocalService syncDeviceLocalService;
	@BeanReference(type = SyncDevicePersistence.class)
	protected SyncDevicePersistence syncDevicePersistence;
	@BeanReference(type = SyncDLFileVersionDiffLocalService.class)
	protected SyncDLFileVersionDiffLocalService syncDLFileVersionDiffLocalService;
	@BeanReference(type = SyncDLFileVersionDiffPersistence.class)
	protected SyncDLFileVersionDiffPersistence syncDLFileVersionDiffPersistence;
	@BeanReference(type = com.liferay.sync.service.SyncDLObjectLocalService.class)
	protected com.liferay.sync.service.SyncDLObjectLocalService syncDLObjectLocalService;
	@BeanReference(type = SyncDLObjectPersistence.class)
	protected SyncDLObjectPersistence syncDLObjectPersistence;
	@BeanReference(type = SyncDLObjectFinder.class)
	protected SyncDLObjectFinder syncDLObjectFinder;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.CompanyLocalService.class)
	protected com.liferay.portal.kernel.service.CompanyLocalService companyLocalService;
	@ServiceReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = com.liferay.document.library.kernel.service.DLAppLocalService.class)
	protected com.liferay.document.library.kernel.service.DLAppLocalService dlAppLocalService;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}