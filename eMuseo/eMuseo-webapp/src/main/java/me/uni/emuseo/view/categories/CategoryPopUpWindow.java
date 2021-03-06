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
package me.uni.emuseo.view.categories;

import me.uni.emuseo.model.categories.CategoryDTO;
import me.uni.emuseo.view.categories.form.CategoryFormLayout;
import me.uni.emuseo.view.common.form.FormBuilder;
import me.uni.emuseo.view.common.form.FormPopUpWindow;
import me.uni.emuseo.view.common.form.InvalidBeanException;

public class CategoryPopUpWindow extends FormPopUpWindow<CategoryDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023778432405550696L;

	public CategoryPopUpWindow(CategoryDTO category, String caption) {
		super(category, caption);
	}

	@Override
	protected FormBuilder<CategoryDTO> createForm(CategoryDTO bean) {
		return new CategoryFormLayout(bean);
	}

	@Override
	protected boolean onSave(CategoryDTO bean) throws InvalidBeanException {
		return false;
	}

}
