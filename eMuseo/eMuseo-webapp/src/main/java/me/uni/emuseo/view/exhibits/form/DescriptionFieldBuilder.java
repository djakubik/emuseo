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

import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;

import me.uni.emuseo.view.common.helpers.FieldBuilder;

public class DescriptionFieldBuilder implements FieldBuilder {

	protected String fieldName;
	
	public DescriptionFieldBuilder(String fieldName) {
		super();
		this.fieldName = fieldName;
	}

	@Override
	public Field<?> build(String propertyId) {
		if (propertyId.equals(fieldName)){
			return new TextArea();
		}
		return null;
	}
}
