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
package me.uni.emuseo.service.impl;

import java.util.ArrayList;
import java.util.List;

import me.uni.emuseo.exception.CannotDeleteCategoryException;
import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.model.categories.CategorySearchDTO;
import me.uni.emuseo.service.CategoryService;
import me.uni.emuseo.service.dao.Alias;
import me.uni.emuseo.service.dao.Dao;
import me.uni.emuseo.service.mapper.CriterionProducer;
import me.uni.emuseo.service.mapper.MapperImpl;
import me.uni.emuseo.service.model.Category;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	protected Dao dao;

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> all = dao.getAll(Category.class);
		List<CategoryDTO> retAll = new ArrayList<CategoryDTO>();
		for (Category category : all) {
			retAll.add(MapperImpl.map(category));
		}
		return retAll;
	}

	@Override
	public Long getCategoriesCount(CategorySearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		return dao.count(Category.class, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
	}

	@Override
	public List<CategoryDTO> getCategories(int firstResult, int maxResults, CategorySearchDTO searchDTO) {
		List<Criterion> criterions = CriterionProducer.getCriterions(searchDTO);
		List<Category> list = dao.get(Category.class, firstResult, maxResults, criterions, new ArrayList<Alias>(0));
		// Arrays.asList(Alias.getInstance("category", "category_")));
		List<CategoryDTO> retAll = new ArrayList<CategoryDTO>(list.size());
		for (Category category : list) {
			retAll.add(MapperImpl.map(category));
		}
		return retAll;
	}

	@Override
	public void addCategory(CategoryDTO category) {
		Category ex = MapperImpl.map(category);
		dao.save(ex);
	}

	@Override
	public void editCategory(CategoryDTO category) {
		Category ex = MapperImpl.map(category);
		dao.saveOrUpdate(ex);
	}

	@Override
	public void deleteCategory(Long id) throws CannotDeleteCategoryException {
		Category category = dao.get(Category.class, id);
		if (category != null && category.getExhibits().size() > 0){
			throw new CannotDeleteCategoryException();
		}
		dao.delete(category);
	}

	@Override
	public CategoryDTO getCategory(Long id) {
		Category category = dao.get(Category.class, id);
		return MapperImpl.map(category);
	};
}
