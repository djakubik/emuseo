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
package me.uni.emuseo.service.generic;

import java.util.List;

public interface BaseService<T> {

	List<T> get(Class<T> type, int firstResult, int maxResults);

	Long count(Class<T> type);

	List<T> getAll(Class<T> type);

	T get(Class<T> type, Long id);

	void delete(T o);

	void saveOrUpdate(T o);

	void save(T o);

}
