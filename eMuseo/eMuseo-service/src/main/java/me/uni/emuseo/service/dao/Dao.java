/*******************************************************************************
 * Copyright (c) 2016 Darian Jakubik.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Darian Jakubik - initial API and implementation
 ******************************************************************************/
package me.uni.emuseo.service.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class Dao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public <T> T save(final T o) {
		return (T) sessionFactory.getCurrentSession().save(o);
	}

	public void delete(final Object object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	/***/
	public <T> T get(final Class<T> type, final Long id) {
		return (T) sessionFactory.getCurrentSession().get(type, id);
	}

	/***/
	public <T> T merge(final T o) {
		return (T) sessionFactory.getCurrentSession().merge(o);
	}

	/***/
	public <T> void saveOrUpdate(final T o) {
		sessionFactory.getCurrentSession().saveOrUpdate(o);
	}

	public <T> List<T> getAll(final Class<T> type) {
		final Session session = sessionFactory.getCurrentSession();
		final Criteria crit = session.createCriteria(type);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	public <T> Long count(final Class<T> type) {
		final Session session = sessionFactory.getCurrentSession();
		return (Long) session.createCriteria(type).setProjection(Projections.rowCount()).uniqueResult();
	}

	public <T> Long count(final Class<T> type, List<Criterion> criterions, List<Alias> aliasses) {
		final Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(type);

		if (criterions != null) {
			for (Criterion criterion : criterions) {
				cr.add(criterion);
			}
			if (aliasses != null && criterions.size() > 0) {
				for (Alias alias : aliasses) {
					cr.createAlias(alias.getAssociationPath(), alias.getAlias());
				}
			}
		}
		return (Long) cr.setProjection(Projections.rowCount()).uniqueResult();
	}

	public <T> List<T> get(final Class<T> type, int firstResult, int maxResults) {
		final Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(type).setFirstResult(firstResult).setMaxResults(maxResults).list();
	}

	public <T> List<T> get(final Class<T> type, int firstResult, int maxResults, List<Criterion> criterions,
			List<Alias> aliasses) {
		final Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(type);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				cr.add(criterion);
			}
			if (aliasses != null && criterions.size() > 0) {
				for (Alias alias : aliasses) {
					cr.createAlias(alias.getAssociationPath(), alias.getAlias());
				}
			}
		}
		return cr.setFirstResult(firstResult).setMaxResults(maxResults).list();
	}
}
