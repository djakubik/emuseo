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
package me.uni.emuseo.service;

import java.util.List;

import me.uni.emuseo.exception.CannotDeleteCategoryException;
import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.model.categories.CategorySearchDTO;

public interface CategoryService {

	CategoryDTO getCategory(Long id);

	void deleteCategory(Long id) throws CannotDeleteCategoryException;

	void editCategory(CategoryDTO category);

	void addCategory(CategoryDTO category);

	List<CategoryDTO> getCategories(int firstResult, int maxResults, CategorySearchDTO searchDTO);

	Long getCategoriesCount(CategorySearchDTO searchDTO);

	List<CategoryDTO> getAllCategories();


}
