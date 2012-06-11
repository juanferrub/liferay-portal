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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Connor McKay
 */
public class JournalArticleFinderImpl
	extends BasePersistenceImpl<JournalArticle>
	implements JournalArticleFinder {

	public static final String COUNT_BY_G_F =
		JournalArticleFinder.class.getName() + ".countByG_F";

	public static final String COUNT_BY_G_F_S =
		JournalArticleFinder.class.getName() + ".countByG_F_S";

	public static final String COUNT_BY_C_G_F_C_A_V_T_D_C_T_S_T_D_S_R =
		JournalArticleFinder.class.getName() +
			".countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R";

	public static final String FIND_BY_EXPIRATION_DATE =
		JournalArticleFinder.class.getName() + ".findByExpirationDate";

	public static final String FIND_BY_REVIEW_DATE =
		JournalArticleFinder.class.getName() + ".findByReviewDate";

	public static final String FIND_BY_R_D =
		JournalArticleFinder.class.getName() + ".findByR_D";

	public static final String FIND_BY_C_G_F_C_A_V_T_D_C_T_S_T_D_S_R =
		JournalArticleFinder.class.getName() +
			".findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R";

	public int countByKeywords(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate)
		throws SystemException {

		String[] articleIds = null;
		String[] titles = null;
		String[] descriptions = null;
		String[] contents = null;
		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleIds = CustomSQLUtil.keywords(keywords, false);
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			contents = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			false);
	}

	public int countByG_F_S(long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return doCountByG_F_S(groupId, folderIds, status, true);
	}

	public int countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);

		return countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator)
		throws SystemException {

		String[] articleIds = CustomSQLUtil.keywords(articleId, false);
		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] contents = CustomSQLUtil.keywords(content, false);

		return countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int countByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			String[] descriptions, String[] contents, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return doCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			false);
	}

	public int filterCountByKeywords(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate)
		throws SystemException {

		String[] articleIds = null;
		String[] titles = null;
		String[] descriptions = null;
		String[] contents = null;
		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleIds = CustomSQLUtil.keywords(keywords, false);
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			contents = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			true);
	}

	public int filterCountByG_F_S(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		return doCountByG_F_S(groupId, folderIds, status, false);
	}

	public int filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);

		return filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			 int status, Date reviewDate, boolean andOperator)
		throws SystemException {

		String[] articleIds = CustomSQLUtil.keywords(articleId, false);
		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] contents = CustomSQLUtil.keywords(content, false);

		return filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator);
	}

	public int filterCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			String[] descriptions, String[] contents, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator)
		throws SystemException {

		return doCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			true);
	}

	public List<JournalArticle> filterFindByKeywords(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] articleIds = null;
		String[] titles = null;
		String[] descriptions = null;
		String[] contents = null;
		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleIds = CustomSQLUtil.keywords(keywords, false);
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			contents = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return doFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator, true);
	}

	public List<JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);

		return filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleId, version,
			title, description, content, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator);
	}

	public List<JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

		String[] articleIds = CustomSQLUtil.keywords(articleId, false);
		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] contents = CustomSQLUtil.keywords(content, false);

		return filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator);
	}

	public List<JournalArticle> filterFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			String[] descriptions, String[] contents, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return doFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator, true);
	}

	public List<JournalArticle> findByExpirationDate(
			long classNameId, int status, Date expirationDateLT)
		throws SystemException {

		Timestamp expirationDateLT_TS = CalendarUtil.getTimestamp(
			expirationDateLT);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_EXPIRATION_DATE);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(
					sql, "(status = ?) AND", StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(expirationDateLT_TS);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticle> findByKeywords(
			long companyId, long groupId, long folderId, long classNameId,
			String keywords, Double version, String type, String structureId,
			String templateId, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] articleIds = null;
		String[] titles = null;
		String[] descriptions = null;
		String[] contents = null;
		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleIds = CustomSQLUtil.keywords(keywords, false);
			titles = CustomSQLUtil.keywords(keywords);
			descriptions = CustomSQLUtil.keywords(keywords, false);
			contents = CustomSQLUtil.keywords(keywords, false);
		}
		else {
			andOperator = true;
		}

		return findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator);
	}

	public List<JournalArticle> findByReviewDate(
			long classNameId, Date reviewDateLT, Date reviewDateGT)
		throws SystemException {

		Timestamp reviewDateLT_TS = CalendarUtil.getTimestamp(reviewDateLT);
		Timestamp reviewDateGT_TS = CalendarUtil.getTimestamp(reviewDateGT);

		Session session = null;
		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_REVIEW_DATE);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(reviewDateGT_TS);
			qPos.add(reviewDateLT_TS);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticle findByR_D(long resourcePrimKey, Date displayDate)
		throws NoSuchArticleException, SystemException {

		Timestamp displayDate_TS = CalendarUtil.getTimestamp(displayDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_R_D);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourcePrimKey);
			qPos.add(displayDate_TS);

			List<JournalArticle> articles = q.list();

			if (!articles.isEmpty()) {
				return articles.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("No JournalArticle exists with the key ");
		sb.append("{resourcePrimKey=");
		sb.append(resourcePrimKey);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append("}");

		throw new NoSuchArticleException(sb.toString());
	}

	public List<JournalArticle> findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String structureId, String templateId,
			Date displayDateGT, Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		String[] articleIds = CustomSQLUtil.keywords(articleId, false);
		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] contents = CustomSQLUtil.keywords(content, false);
		String[] structureIds = CustomSQLUtil.keywords(structureId, false);
		String[] templateIds = CustomSQLUtil.keywords(templateId, false);

		return findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator);
	}

	public List<JournalArticle> findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String articleId, Double version, String title, String description,
			String content, String type, String[] structureIds,
			String[] templateIds, Date displayDateGT, Date displayDateLT,
			int status, Date reviewDate, boolean andOperator, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

		String[] articleIds = CustomSQLUtil.keywords(articleId, false);
		String[] titles = CustomSQLUtil.keywords(title);
		String[] descriptions = CustomSQLUtil.keywords(description, false);
		String[] contents = CustomSQLUtil.keywords(content, false);

		return findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator);
	}

	public List<JournalArticle> findByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			 String[] descriptions, String[] contents, String type,
			 String[] structureIds, String[] templateIds, Date displayDateGT,
			 Date displayDateLT, int status, Date reviewDate,
			 boolean andOperator, int start, int end,
			 OrderByComparator orderByComparator)
		throws SystemException {

		return doFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			companyId, groupId, folderId, classNameId, articleIds, version,
			titles, descriptions, contents, type, structureIds, templateIds,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, orderByComparator, false);
	}

	protected int doCountByG_F_S(
			long groupId, List<Long> folderIds, int status,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = null;

			String table = "JournalArticle";

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = CustomSQLUtil.get(COUNT_BY_G_F);
			}
			else {
				sql = CustomSQLUtil.get(COUNT_BY_G_F_S);
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);
			}

			sql = StringUtil.replace(
				sql, "[$FOLDER_ID$]", getFolderIds(folderIds, table));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			for (int i = 0; i < folderIds.size(); i++) {
				Long folderId = folderIds.get(i);

				qPos.add(folderId);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected int doCountByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			String[] descriptions, String[] contents, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, boolean inlineSQLHelper)
		throws SystemException {

		articleIds = CustomSQLUtil.keywords(articleIds, false);
		titles = CustomSQLUtil.keywords(titles);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		contents = CustomSQLUtil.keywords(contents, false);
		structureIds = CustomSQLUtil.keywords(structureIds, false);
		templateIds = CustomSQLUtil.keywords(templateIds, false);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);
		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp reviewDate_TS = CalendarUtil.getTimestamp(reviewDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				COUNT_BY_C_G_F_C_A_V_T_D_C_T_S_T_D_S_R);

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(groupId = ?) AND", StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "articleId", StringPool.LIKE, false, articleIds);

			if ((version == null) || (version <= 0)) {
				sql = StringUtil.replace(
					sql, "(version = ?) [$AND_OR_CONNECTOR$]",
					StringPool.BLANK);
			}

			if (folderId < 0) {
				sql = StringUtil.replace(sql, "(folderId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(title)", StringPool.LIKE, false, titles);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "content", StringPool.LIKE, false, contents);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(
					sql, "(status = ?) AND", StringPool.BLANK);
			}

			sql = StringUtil.replace(
				sql, "[$WHERE_CLAUSE$]",
				getWhereClause(type, structureIds, templateIds));

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if ((articleIds != null) &&
				((articleIds.length > 1) ||
				 ((articleIds.length == 1) && (articleIds[0] != null))) &&
				(version == null)) {

				sql = StringUtil.replace(
					sql, "MAX(version) AS tempVersion",
					"version AS tempVersion");
				sql = StringUtil.replace(
					sql, "[$GROUP_BY_CLAUSE$]", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "[$GROUP_BY_CLAUSE$]", "GROUP BY groupId, articleId");
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);

				sql = StringUtil.replace(
					sql, "(companyId", "(JournalArticle.companyId");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(classNameId);
			qPos.add(articleIds, 2);

			if ((version != null) && (version > 0)) {
				qPos.add(version);
			}

			qPos.add(titles, 2);
			qPos.add(descriptions, 2);
			qPos.add(contents, 2);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);
			qPos.add(displayDateLT_TS);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(reviewDate_TS);
			qPos.add(reviewDate_TS);

			if (Validator.isNotNull(type)) {
				qPos.add(type);
				qPos.add(type);
			}

			if (!isArrayNull(structureIds)) {
				qPos.add(structureIds, 2);
			}

			if (!isArrayNull(templateIds)) {
				qPos.add(templateIds, 2);
			}

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<JournalArticle> doFindByC_G_F_C_A_V_T_D_C_T_S_T_D_S_R(
			long companyId, long groupId, long folderId, long classNameId,
			String[] articleIds, Double version, String[] titles,
			String[] descriptions, String[] contents, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, int status, Date reviewDate,
			boolean andOperator, int start, int end,
			OrderByComparator orderByComparator, boolean inlineSQLHelper)
		throws SystemException {

		articleIds = CustomSQLUtil.keywords(articleIds, false);
		titles = CustomSQLUtil.keywords(titles);
		descriptions = CustomSQLUtil.keywords(descriptions, false);
		contents = CustomSQLUtil.keywords(contents, false);
		structureIds = CustomSQLUtil.keywords(structureIds, false);
		templateIds = CustomSQLUtil.keywords(templateIds, false);
		Timestamp displayDateGT_TS = CalendarUtil.getTimestamp(displayDateGT);
		Timestamp displayDateLT_TS = CalendarUtil.getTimestamp(displayDateLT);
		Timestamp reviewDate_TS = CalendarUtil.getTimestamp(reviewDate);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				FIND_BY_C_G_F_C_A_V_T_D_C_T_S_T_D_S_R);

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(groupId = ?) AND", StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "articleId", StringPool.LIKE, false, articleIds);

			if ((version == null) || (version <= 0)) {
				sql = StringUtil.replace(
					sql, "(version = ?) [$AND_OR_CONNECTOR$]",
					StringPool.BLANK);
			}

			if (folderId < 0) {
				sql = StringUtil.replace(sql, "(folderId = ?) AND", "");
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(title)", StringPool.LIKE, false, titles);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "description", StringPool.LIKE, false, descriptions);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "content", StringPool.LIKE, false, contents);

			if (status == WorkflowConstants.STATUS_ANY) {
				sql = StringUtil.replace(
					sql, "(status = ?) AND", StringPool.BLANK);
			}

			sql = StringUtil.replace(
				sql, "[$WHERE_CLAUSE$]",
				getWhereClause(type, structureIds, templateIds));

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if ((articleIds != null) &&
				((articleIds.length > 1) ||
				 ((articleIds.length == 1) && (articleIds[0] != null))) &&
				(version == null)) {

				sql = StringUtil.replace(
					sql, "MAX(version) AS tempVersion",
					"version AS tempVersion");
				sql = StringUtil.replace(
					sql, "[$GROUP_BY_CLAUSE$]", StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, "[$GROUP_BY_CLAUSE$]", "GROUP BY groupId, articleId");
			}

			sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, JournalArticle.class.getName(),
					"JournalArticle.resourcePrimKey", groupId);

				sql = StringUtil.replace(
					sql, "(companyId", "(JournalArticle.companyId");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("JournalArticle", JournalArticleImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (folderId >= 0) {
				qPos.add(folderId);
			}

			qPos.add(classNameId);
			qPos.add(articleIds, 2);

			if ((version != null) && (version > 0)) {
				qPos.add(version);
			}

			qPos.add(titles, 2);
			qPos.add(descriptions, 2);
			qPos.add(contents, 2);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateGT_TS);
			qPos.add(displayDateLT_TS);
			qPos.add(displayDateLT_TS);

			if (status != WorkflowConstants.STATUS_ANY) {
				qPos.add(status);
			}

			qPos.add(reviewDate_TS);
			qPos.add(reviewDate_TS);

			if (Validator.isNotNull(type)) {
				qPos.add(type);
				qPos.add(type);
			}

			if (!isArrayNull(structureIds)) {
				qPos.add(structureIds, 2);
			}

			if (!isArrayNull(templateIds)) {
				qPos.add(templateIds, 2);
			}

			return (List<JournalArticle>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getFolderIds(List<Long> folderIds, String table) {
		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(folderIds.size() * 2 - 1);

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append(table);
			sb.append(".folderId = ? ");

			if ((i + 1) != folderIds.size()) {
				sb.append(WHERE_OR);
			}
		}

		return sb.toString();
	}

	protected JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws SystemException {

		List<JournalArticle> articles = null;

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = JournalArticleUtil.findByG_A(groupId, articleId, 0, 1);
		}
		else {
			articles = JournalArticleUtil.findByG_A_ST(
				groupId, articleId, status, 0, 1);
		}

		if (articles.isEmpty()) {
			return null;
		}

		return articles.get(0);
	}

	protected String getWhereClause(
		String type, String[] structureIds, String[] templateIds) {

		boolean isNullStructureIds = isArrayNull(structureIds);
		boolean isNullTemplateIds = isArrayNull(templateIds);

		if (Validator.isNull(type) && isNullStructureIds && isNullTemplateIds) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("WHERE");
		sb.append(StringPool.SPACE);

		if (!Validator.isNull(type)) {
			sb.append(_TYPE_SQL);
			sb.append(StringPool.SPACE);

			if (!isNullStructureIds || !isNullTemplateIds) {
				sb.append(_AND_OR_CONNECTORS);
				sb.append(StringPool.SPACE);
			}
		}

		if (!isNullStructureIds) {
			sb.append(_STRUCTUREID_SQL);
			sb.append(StringPool.SPACE);

			if (!isNullTemplateIds) {
				sb.append(_AND_OR_CONNECTORS);
				sb.append(StringPool.SPACE);
			}
		}

		if (!isNullTemplateIds) {
			sb.append(_TEMPLATEID_SQL);
			sb.append(StringPool.SPACE);
		}

		return sb.toString();
	}

	protected boolean isArrayNull(Object[] array) {
		if (Validator.isNull(array)) {
			return true;
		}

		for (Object obj : array) {
			if (Validator.isNotNull(obj)) {
				return false;
			}
		}

		return true;
	}

	private static final String _AND_OR_CONNECTORS = "[$AND_OR_CONNECTOR$]";

	private static final String _STRUCTUREID_SQL =
		"(structureId LIKE ? [$AND_OR_NULL_CHECK$])";

	private static final String _TEMPLATEID_SQL =
		"(templateId LIKE ? [$AND_OR_NULL_CHECK$])";

	private static final String _TYPE_SQL = "(type_ = ? [$AND_OR_NULL_CHECK$])";

}