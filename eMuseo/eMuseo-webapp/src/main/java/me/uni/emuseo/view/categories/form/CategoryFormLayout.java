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
package me.uni.emuseo.view.categories.form;

import java.util.Arrays;
import java.util.List;

import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class CategoryFormLayout extends FormBuilder<CategoryDTO> {

	private static final long serialVersionUID = -3757793442693138426L;

	protected CategoryDTO categoryBean;

	public CategoryFormLayout() {
		categoryBean = new CategoryDTO();
		init2();
	}

	public CategoryFormLayout(CategoryDTO category) {
		this.categoryBean = category;
		init2();
	}

	protected void init2() {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("name") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").setWidth(300).put("name", "Nazwa");
		addFieldConfigurator(fieldConfiguratorCaptionImpl);
		setBean(categoryBean, fields);
	}

}
