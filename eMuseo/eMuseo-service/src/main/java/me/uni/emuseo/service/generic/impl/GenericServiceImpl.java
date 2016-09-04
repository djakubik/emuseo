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
import me.uni.emuseo.service.generic.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("genericService")
@Transactional
public class GenericServiceImpl implements GenericService {

	@Autowired
	private Dao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.uni.emuseo.service.impl.GenericService#save(T)
	 */
	@Override
	public <T> void save(T o) {
		dao.save(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.uni.emuseo.service.impl.GenericService#saveOrUpdate(T)
	 */
	@Override
	public <T> void saveOrUpdate(T o) {
		dao.saveOrUpdate(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.uni.emuseo.service.impl.GenericService#delete(T)
	 */
	@Override
	public <T> void delete(T o) {
		dao.delete(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.uni.emuseo.service.impl.GenericService#get(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public <T> T get(Class<T> type, Long id) {
		return dao.get(type, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * me.uni.emuseo.service.impl.GenericService#getAll(java.lang.Class,
	 * java.lang.Long)
	 */
	@Override
	public <T> List<T> getAll(Class<T> type) {
		return dao.getAll(type);
	}

	@Override
	public <T> Long count(Class<T> type) {
		return dao.count(type);
	}
	
	@Override
	public <T> List<T> get(Class<T> type, int firstResult, int maxResults) {
		return dao.get(type, firstResult, maxResults);
	}

}
