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

import me.uni.emuseo.model.categories.CategorySearchDTO;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.SearchFormLayout;
import me.uni.emuseo.view.common.helpers.FieldConfiguratorCaptionImpl;

public class CategorySearchFormLayout extends SearchFormLayout<CategorySearchDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2216704103494439977L;

	@Override
	public CategorySearchDTO getBean() {
		return new CategorySearchDTO();
	}

	@Override
	protected List<String>[] configureFormBuilder(CategorySearchDTO initBean, FormBuilder<CategorySearchDTO> formBuilder) {
		@SuppressWarnings("unchecked")
		List<String>[] fields = new List[] { Arrays.asList("name") };

		FieldConfiguratorCaptionImpl fieldConfiguratorCaptionImpl = new FieldConfiguratorCaptionImpl()
				.setNullRepresentation("").put("name", "Nazwa");
		formBuilder.addFieldConfigurator(fieldConfiguratorCaptionImpl);
		return fields;
	}

}
