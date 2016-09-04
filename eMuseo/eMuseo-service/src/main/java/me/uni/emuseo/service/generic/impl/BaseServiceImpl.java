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
package me.uni.emuseo.service.generic.impl;

import java.util.List;

import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.generic.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("baseService")
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

	@Autowired
	protected Dao dao;

	@Override
	public void save(T o) {
		dao.save(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		dao.saveOrUpdate(o);
	}

	@Override
	public void delete(T o) {
		dao.delete(o);
	}

	@Override
	public T get(Class<T> type, Long id) {
		return dao.get(type, id);
	}

	@Override
	public List<T> getAll(Class<T> type) {
		return dao.getAll(type);
	}

	@Override
	public Long count(Class<T> type) {
		return dao.count(type);
	}

	@Override
	public List<T> get(Class<T> type, int firstResult, int maxResults) {
		return dao.get(type, firstResult, maxResults);
	}

}
