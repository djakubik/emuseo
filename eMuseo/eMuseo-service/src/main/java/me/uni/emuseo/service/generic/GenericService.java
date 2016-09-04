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

public interface GenericService {

	<T> void save(T o);

	<T> void saveOrUpdate(T o);

	<T> void delete(T o);

	<T> T get(Class<T> type, Long id);

	<T> List<T> getAll(Class<T> type);

	<T> Long count(Class<T> type);

	<T> List<T> get(Class<T> type, int firstResult, int maxResults);

}
