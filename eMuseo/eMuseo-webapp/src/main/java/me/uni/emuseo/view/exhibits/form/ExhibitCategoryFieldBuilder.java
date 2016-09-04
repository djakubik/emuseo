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
package me.uni.emuseo.view.exhibits.form;

import java.util.Collection;

import me.uni.emuseo.EMuseoUtil;
import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.service.CategoryService;
import me.uni.emuseo.view.common.helpers.FieldBuilder;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;

class ExhibitCategoryFieldBuilder implements FieldBuilder {
	private Collection<CategoryDTO> items;
	private String categoryPropertyId = "exhibitCategory";

	public ExhibitCategoryFieldBuilder() {
		super();
		CategoryService categoryService = EMuseoUtil.getAppContext().getBean(CategoryService.class);
		this.items = categoryService.getAllCategories();
	}

	public ExhibitCategoryFieldBuilder(String categoryPropertyId) {
		this();
		this.categoryPropertyId = categoryPropertyId;
	}

	public ExhibitCategoryFieldBuilder(Collection<CategoryDTO> items) {
		super();
		this.items = items;
	}

	public ExhibitCategoryFieldBuilder(Collection<CategoryDTO> items, String categoryPropertyId) {
		super();
		this.items = items;
		this.categoryPropertyId = categoryPropertyId;
	}

	@Override
	public Field<?> build(String propertyId) {
		if (propertyId.equals(categoryPropertyId)) {
			ComboBox comboBox = new ComboBox();
			comboBox.setNullSelectionAllowed(false);
			comboBox.addItems(items);
			for (CategoryDTO categoryDTO : items) {
				comboBox.setItemCaption(categoryDTO, categoryDTO.getName());
			}
			return comboBox;
		}
		return null;
	}

}
